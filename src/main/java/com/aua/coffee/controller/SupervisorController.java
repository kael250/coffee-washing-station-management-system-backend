package com.aua.coffee.controller;

import com.aua.coffee.domain.Supervisor;
import com.aua.coffee.service.SupervisorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/supervisors")
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Supervisor supervisor) {
        String message = supervisorService.save(supervisor);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Supervisor>> all() {
        return ResponseEntity.ok(supervisorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Supervisor> supervisor = supervisorService.findById(id);
        if (supervisor.isPresent()) {
            return ResponseEntity.ok(supervisor.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The supervisor with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Supervisor supervisor) {
        Optional<Supervisor> result = supervisorService.update(id, supervisor);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The supervisor with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = supervisorService.delete(id);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The supervisor with that id does not exist");
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-skill")
    public ResponseEntity<List<Supervisor>> bySkill(@RequestParam String name) {
        return ResponseEntity.ok(supervisorService.findBySkillName(name));
    }

    @PatchMapping("/{supervisorId}/drying-table")
    public ResponseEntity<?> moveToDryingTable(@PathVariable UUID supervisorId, @RequestParam Integer tableNumber) {
        String message = supervisorService.moveToDryingTable(supervisorId, tableNumber);
        if (message.equals("The supervisor with that id does not exist") || message.equals("The drying table with that tableNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("That drying table is taken by another supervisor")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{supervisorId}/skills/{skillId}")
    public ResponseEntity<?> addSkill(@PathVariable UUID supervisorId, @PathVariable UUID skillId) {
        String message = supervisorService.addSkill(supervisorId, skillId);
        if (message.equals("The supervisor with that id does not exist") || message.equals("That skill does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("The supervisor already has that skill")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
