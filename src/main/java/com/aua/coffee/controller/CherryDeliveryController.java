package com.aua.coffee.controller;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.DeliveryStatus;
import com.aua.coffee.service.CherryDeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cherry-deliveries")
public class CherryDeliveryController {

    private final CherryDeliveryService cherryDeliveryService;

    public CherryDeliveryController(CherryDeliveryService cherryDeliveryService) {
        this.cherryDeliveryService = cherryDeliveryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CherryDelivery cherryDelivery) {
        String message = cherryDeliveryService.save(cherryDelivery);
        HttpStatus status = HttpStatus.CREATED;
        if (message.equals("Cherry delivery date cannot be in the past")) {
            status = HttpStatus.BAD_REQUEST;
        }
        if (message.equals("Supervisor already handles a delivery on that date")) {
            status = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(status).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CherryDelivery>> all() {
        return ResponseEntity.ok(cherryDeliveryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable UUID id) {
        Optional<CherryDelivery> cherryDelivery = cherryDeliveryService.findById(id);
        if (cherryDelivery.isPresent()) {
            return ResponseEntity.ok(cherryDelivery.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The cherry delivery with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody CherryDelivery cherryDelivery) {
        Optional<CherryDelivery> result = cherryDeliveryService.update(id, cherryDelivery);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The cherry delivery with that id does not exist");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam DeliveryStatus status) {
        String message = cherryDeliveryService.changeStatus(id, status);
        if (message.equals("The cherry delivery with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("Cannot change status from")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = cherryDeliveryService.delete(id);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The cherry delivery with that id does not exist");
        }
        if (message.equals("That cherry delivery has parchment lots, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<CherryDelivery>> byStatus(@RequestParam DeliveryStatus status) {
        return ResponseEntity.ok(cherryDeliveryService.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<CherryDelivery>> between(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return ResponseEntity.ok(cherryDeliveryService.findBetween(start, end));
    }
}
