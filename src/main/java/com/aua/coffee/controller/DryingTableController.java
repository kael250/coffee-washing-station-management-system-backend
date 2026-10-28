package com.aua.coffee.controller;

import com.aua.coffee.domain.DryingTable;
import com.aua.coffee.service.DryingTableService;
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
@RequestMapping("/api/drying-tables")
public class DryingTableController {

    private final DryingTableService dryingTableService;

    public DryingTableController(DryingTableService dryingTableService) {
        this.dryingTableService = dryingTableService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody DryingTable dryingTable) {
        String message = dryingTableService.save(dryingTable);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DryingTable>> all() {
        return ResponseEntity.ok(dryingTableService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<DryingTable> dryingTable = dryingTableService.findById(id);
        if (dryingTable.isPresent()) {
            return ResponseEntity.ok(dryingTable.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The drying table with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody DryingTable dryingTable) {
        Optional<DryingTable> result = dryingTableService.update(id, dryingTable);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The drying table with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = dryingTableService.delete(id);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The drying table with that id does not exist");
        }
        return ResponseEntity.ok(message);
    }
}
