package com.aua.coffee.service;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.DeliveryStatus;
import com.aua.coffee.repository.CherryDeliveryRepository;
import com.aua.coffee.repository.ParchmentLotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CherryDeliveryService {

    private final CherryDeliveryRepository cherryDeliveryRepository;
    private final ParchmentLotRepository parchmentLotRepository;

    public CherryDeliveryService(CherryDeliveryRepository cherryDeliveryRepository, ParchmentLotRepository parchmentLotRepository) {
        this.cherryDeliveryRepository = cherryDeliveryRepository;
        this.parchmentLotRepository = parchmentLotRepository;
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
        Optional<CherryDelivery> cherryDelivery = cherryDeliveryRepository.findById(id);
        if (cherryDelivery.isEmpty()) {
            return null;
        }
        if (parchmentLotRepository.existsByCherryDelivery(cherryDelivery.get())) {
            return "That cherry delivery has parchment lots, delete them first";
        }
        cherryDeliveryRepository.deleteById(id);
        return "Cherry delivery deleted successfully";
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

    public List<Object[]> findStatsByStatus() {
        return cherryDeliveryRepository.findStatsByStatus();
    }

    @Transactional
    public String cancelDay(UUID supervisorId, LocalDate date) {
        int count = cherryDeliveryRepository.cancelDay(supervisorId, date);
        return count + " cherry deliveries cancelled";
    }
}
