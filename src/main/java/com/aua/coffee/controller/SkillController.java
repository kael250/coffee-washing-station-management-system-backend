package com.aua.coffee.controller;

import com.aua.coffee.domain.Skill;
import com.aua.coffee.service.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Skill skill) {
        String message = skillService.save(skill);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Skill>> all() {
        return ResponseEntity.ok(skillService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Skill> skill = skillService.findById(id);
        if (skill.isPresent()) {
            return ResponseEntity.ok(skill.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The skill with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Skill skill) {
        Optional<Skill> result = skillService.update(id, skill);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The skill with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = skillService.delete(id);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The skill with that id does not exist");
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Skill>> unused() {
        return ResponseEntity.ok(skillService.findUnused());
    }
}
