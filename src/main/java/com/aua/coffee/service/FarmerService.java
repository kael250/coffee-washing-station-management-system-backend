package com.aua.coffee.service;

import com.aua.coffee.domain.Farmer;
import com.aua.coffee.repository.FarmerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;

    public FarmerService(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    public String save(Farmer farmer) {
        farmerRepository.save(farmer);
        return "Farmer saved successfully";
    }

    public List<Farmer> findAll() {
        return farmerRepository.findAll();
    }

    public Optional<Farmer> findById(UUID id) {
        return farmerRepository.findById(id);
    }

    public Optional<Farmer> update(UUID id, Farmer farmer) {
        return farmerRepository.findById(id).map(existing -> {
            existing.setFirstName(farmer.getFirstName());
            existing.setLastName(farmer.getLastName());
            existing.setFarmerCode(farmer.getFarmerCode());
            existing.setVillage(farmer.getVillage());
            existing.setPhone(farmer.getPhone());
            return farmerRepository.save(existing);
        });
    }

    public String delete(UUID id) {
        if (farmerRepository.existsById(id)) {
            farmerRepository.deleteById(id);
            return "Farmer deleted successfully";
        }
        return null;
    }
}
