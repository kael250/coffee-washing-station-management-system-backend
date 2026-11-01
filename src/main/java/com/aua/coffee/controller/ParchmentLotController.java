package com.aua.coffee.controller;

import com.aua.coffee.domain.ParchmentLot;
import com.aua.coffee.service.ParchmentLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parchment-lots")
public class ParchmentLotController {

    private final ParchmentLotService parchmentLotService;

    public ParchmentLotController(ParchmentLotService parchmentLotService) {
        this.parchmentLotService = parchmentLotService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam UUID cherryDeliveryId, @RequestBody ParchmentLot parchmentLot) {
        String message = parchmentLotService.save(cherryDeliveryId, parchmentLot);
        if (message.equals("The cherry delivery with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("Parchment lots are only for DRIED cherry deliveries")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/farmer/{farmerCode}")
    public ResponseEntity<List<ParchmentLot>> farmerParchmentLots(@PathVariable String farmerCode) {
        return ResponseEntity.ok(parchmentLotService.findByFarmerCode(farmerCode));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = parchmentLotService.delete(id);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The parchment lot with that id does not exist");
        }
        return ResponseEntity.ok(message);
    }
}
