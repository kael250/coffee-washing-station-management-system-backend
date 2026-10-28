package com.aua.coffee.service;

import com.aua.coffee.domain.DryingTable;
import com.aua.coffee.repository.DryingTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DryingTableService {

    private final DryingTableRepository dryingTableRepository;

    public DryingTableService(DryingTableRepository dryingTableRepository) {
        this.dryingTableRepository = dryingTableRepository;
    }

    public String save(DryingTable dryingTable) {
        dryingTableRepository.save(dryingTable);
        return "Drying table saved successfully";
    }

    public List<DryingTable> findAll() {
        return dryingTableRepository.findAll();
    }

    public Optional<DryingTable> findById(UUID id) {
        return dryingTableRepository.findById(id);
    }

    public Optional<DryingTable> update(UUID id, DryingTable dryingTable) {
        return dryingTableRepository.findById(id).map(existing -> {
            existing.setTableNumber(dryingTable.getTableNumber());
            existing.setName(dryingTable.getName());
            return dryingTableRepository.save(existing);
        });
    }

    public String delete(UUID id) {
        if (dryingTableRepository.existsById(id)) {
            dryingTableRepository.deleteById(id);
            return "Drying table deleted successfully";
        }
        return null;
    }
}
