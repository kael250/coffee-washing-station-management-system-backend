package com.aua.coffee.controller;

import com.aua.coffee.domain.Farmer;
import com.aua.coffee.domain.Supervisor;
import com.aua.coffee.service.FarmerService;
import com.aua.coffee.service.SupervisorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    private final FarmerService farmerService;
    private final SupervisorService supervisorService;

    public FarmerController(FarmerService farmerService, SupervisorService supervisorService) {
        this.farmerService = farmerService;
        this.supervisorService = supervisorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Farmer farmer) {
        String message = farmerService.save(farmer);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Farmer>> all() {
        return ResponseEntity.ok(farmerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<Farmer> farmer = farmerService.findById(id);
        if (farmer.isPresent()) {
            return ResponseEntity.ok(farmer.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The farmer with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Farmer farmer) {
        Optional<Farmer> result = farmerService.update(id, farmer);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The farmer with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = farmerService.delete(id);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The farmer with that id does not exist");
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-village")
    public ResponseEntity<List<Farmer>> byVillage(@RequestParam String village) {
        return ResponseEntity.ok(farmerService.findByVillageIgnoreCase(village));
    }

    @GetMapping("/of-supervisor/{supervisorId}")
    public ResponseEntity<?> farmersOfSupervisor(@PathVariable UUID supervisorId) {
        Optional<Supervisor> supervisor = supervisorService.findById(supervisorId);
        if (supervisor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The supervisor with that id does not exist");
        }
        return ResponseEntity.ok(supervisorService.findFarmersBySupervisor(supervisorId));
    }
}
