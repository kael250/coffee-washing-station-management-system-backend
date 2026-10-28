package com.aua.coffee.service;

import com.aua.coffee.domain.Supervisor;
import com.aua.coffee.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    public String save(Supervisor supervisor) {
        if (supervisor.getSkills() == null) {
            supervisor.setSkills(new ArrayList<>());
        }
        supervisorRepository.save(supervisor);
        return "Supervisor saved successfully";
    }

    public List<Supervisor> findAll() {
        return supervisorRepository.findAll();
    }

    public Optional<Supervisor> findById(UUID id) {
        return supervisorRepository.findById(id);
    }

    public Optional<Supervisor> update(UUID id, Supervisor supervisor) {
        return supervisorRepository.findById(id).map(existing -> {
            existing.setFirstName(supervisor.getFirstName());
            existing.setLastName(supervisor.getLastName());
            existing.setPhone(supervisor.getPhone());
            existing.setDryingTable(supervisor.getDryingTable());
            return supervisorRepository.save(existing);
        });
    }

    public String delete(UUID id) {
        if (supervisorRepository.existsById(id)) {
            supervisorRepository.deleteById(id);
            return "Supervisor deleted successfully";
        }
        return null;
    }
}
