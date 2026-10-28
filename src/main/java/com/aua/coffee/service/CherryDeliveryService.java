package com.aua.coffee.service;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.repository.CherryDeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CherryDeliveryService {

    private final CherryDeliveryRepository cherryDeliveryRepository;

    public CherryDeliveryService(CherryDeliveryRepository cherryDeliveryRepository) {
        this.cherryDeliveryRepository = cherryDeliveryRepository;
    }

    public String save(CherryDelivery cherryDelivery) {
        cherryDeliveryRepository.save(cherryDelivery);
        return "Cherry delivery saved successfully";
    }

    public List<CherryDelivery> findAll() {
        return cherryDeliveryRepository.findAll();
    }

    public Optional<CherryDelivery> findById(UUID id) {
        return cherryDeliveryRepository.findById(id);
    }

    public Optional<CherryDelivery> update(UUID id, CherryDelivery cherryDelivery) {
        return cherryDeliveryRepository.findById(id).map(existing -> {
            existing.setDeliveryDate(cherryDelivery.getDeliveryDate());
            existing.setCherryKg(cherryDelivery.getCherryKg());
            existing.setFarmer(cherryDelivery.getFarmer());
            existing.setSupervisor(cherryDelivery.getSupervisor());
            return cherryDeliveryRepository.save(existing);
        });
    }

    public String delete(UUID id) {
        if (cherryDeliveryRepository.existsById(id)) {
            cherryDeliveryRepository.deleteById(id);
            return "Cherry delivery deleted successfully";
        }
        return null;
    }
}
