package com.aua.coffee.service;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.DeliveryStatus;
import com.aua.coffee.repository.CherryDeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        if (cherryDelivery.getDeliveryDate() == null || cherryDelivery.getDeliveryDate().isBefore(LocalDate.now())) {
            return "Cherry delivery date cannot be in the past";
        }
        if (cherryDelivery.getSupervisor() != null && cherryDelivery.getSupervisor().getId() != null) {
            boolean busy = cherryDeliveryRepository.existsBySupervisorIdAndDeliveryDateAndStatusIsNot(
                    cherryDelivery.getSupervisor().getId(), cherryDelivery.getDeliveryDate(), DeliveryStatus.REJECTED);
            if (busy) {
                return "Supervisor already handles a delivery on that date";
            }
        }
        cherryDelivery.setStatus(DeliveryStatus.RECEIVED);
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

    public List<CherryDelivery> findByStatus(DeliveryStatus status) {
        return cherryDeliveryRepository.findByStatusOrderByDeliveryDateAsc(status);
    }

    @Transactional
    public String changeStatus(UUID id, DeliveryStatus newStatus) {
        Optional<CherryDelivery> delivery = cherryDeliveryRepository.findById(id);
        if (delivery.isEmpty()) {
            return "The cherry delivery with that id does not exist";
        }
        CherryDelivery cherryDelivery = delivery.get();
        DeliveryStatus current = cherryDelivery.getStatus();
        if (current == DeliveryStatus.DRIED || current == DeliveryStatus.REJECTED) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        if (current == DeliveryStatus.RECEIVED) {
            if (newStatus != DeliveryStatus.WASHING && newStatus != DeliveryStatus.REJECTED) {
                return "Cannot change status from " + current + " to " + newStatus;
            }
        }
        if (current == DeliveryStatus.WASHING) {
            if (newStatus != DeliveryStatus.DRIED && newStatus != DeliveryStatus.REJECTED) {
                return "Cannot change status from " + current + " to " + newStatus;
            }
        }
        cherryDelivery.setStatus(newStatus);
        cherryDeliveryRepository.save(cherryDelivery);
        return "Cherry delivery status updated successfully";
    }

    public List<CherryDelivery> findBetween(LocalDate start, LocalDate end) {
        return cherryDeliveryRepository.findByDeliveryDateBetweenOrderByDeliveryDateAsc(start, end);
    }
}
