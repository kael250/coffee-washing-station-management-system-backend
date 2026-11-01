package com.aua.coffee.service;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.DeliveryStatus;
import com.aua.coffee.domain.ParchmentLot;
import com.aua.coffee.repository.CherryDeliveryRepository;
import com.aua.coffee.repository.ParchmentLotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParchmentLotService {

    private final ParchmentLotRepository parchmentLotRepository;
    private final CherryDeliveryRepository cherryDeliveryRepository;

    public ParchmentLotService(ParchmentLotRepository parchmentLotRepository, CherryDeliveryRepository cherryDeliveryRepository) {
        this.parchmentLotRepository = parchmentLotRepository;
        this.cherryDeliveryRepository = cherryDeliveryRepository;
    }

    public String save(UUID cherryDeliveryId, ParchmentLot parchmentLot) {
        Optional<CherryDelivery> cherryDeliveryOpt = cherryDeliveryRepository.findById(cherryDeliveryId);
        if (cherryDeliveryOpt.isEmpty()) {
            return "The cherry delivery with that id does not exist";
        }
        if (cherryDeliveryOpt.get().getStatus() != DeliveryStatus.DRIED) {
            return "Parchment lots are only for DRIED cherry deliveries";
        }
        parchmentLot.setCherryDelivery(cherryDeliveryOpt.get());
        parchmentLot.setRecordedDate(LocalDate.now());
        parchmentLotRepository.save(parchmentLot);
        return "Parchment lot saved successfully";
    }

    public List<ParchmentLot> findByFarmerCode(String farmerCode) {
        return parchmentLotRepository.findByFarmerCode(farmerCode);
    }

    public String delete(UUID id) {
        if (parchmentLotRepository.existsById(id)) {
            parchmentLotRepository.deleteById(id);
            return "Parchment lot deleted successfully";
        }
        return null;
    }
}
