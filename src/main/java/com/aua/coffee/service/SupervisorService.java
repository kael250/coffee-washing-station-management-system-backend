package com.aua.coffee.service;

import com.aua.coffee.domain.DryingTable;
import com.aua.coffee.domain.Farmer;
import com.aua.coffee.domain.Skill;
import com.aua.coffee.domain.Supervisor;
import com.aua.coffee.repository.DryingTableRepository;
import com.aua.coffee.repository.SkillRepository;
import com.aua.coffee.repository.SupervisorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final SkillRepository skillRepository;
    private final DryingTableRepository dryingTableRepository;

    public SupervisorService(SupervisorRepository supervisorRepository, SkillRepository skillRepository, DryingTableRepository dryingTableRepository) {
        this.supervisorRepository = supervisorRepository;
        this.skillRepository = skillRepository;
        this.dryingTableRepository = dryingTableRepository;
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

    public List<Supervisor> findBySkillName(String name) {
        return supervisorRepository.findBySkillName(name);
    }

    public List<Farmer> findFarmersBySupervisor(UUID supervisorId) {
        return supervisorRepository.findFarmersBySupervisor(supervisorId);
    }

    @Transactional
    public String moveToDryingTable(UUID supervisorId, Integer tableNumber) {
        Optional<Supervisor> supervisorOpt = supervisorRepository.findById(supervisorId);
        if (supervisorOpt.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
        Optional<DryingTable> dryingTableOpt = dryingTableRepository.findByTableNumber(tableNumber);
        if (dryingTableOpt.isEmpty()) {
            return "The drying table with that tableNumber does not exist";
        }
        List<Supervisor> occupants = supervisorRepository.findByDryingTable(dryingTableOpt.get());
        boolean taken = false;
        for (Supervisor s : occupants) {
            if (s.getId() != null && !s.getId().equals(supervisorId)) {
                taken = true;
            }
        }
        if (taken) {
            return "That drying table is taken by another supervisor";
        }
        Supervisor supervisor = supervisorOpt.get();
        supervisor.setDryingTable(dryingTableOpt.get());
        supervisorRepository.save(supervisor);
        return "Drying table changed successfully";
    }

    @Transactional
    public String addSkill(UUID supervisorId, UUID skillId) {
        Optional<Supervisor> supervisorOpt = supervisorRepository.findById(supervisorId);
        if (supervisorOpt.isEmpty()) {
            return "The supervisor with that id does not exist";
        }
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        if (skillOpt.isEmpty()) {
            return "That skill does not exist";
        }
        Supervisor supervisor = supervisorOpt.get();
        if (supervisor.getSkills() == null) {
            supervisor.setSkills(new ArrayList<>());
        }
        if (supervisor.getSkills().contains(skillOpt.get())) {
            return "The supervisor already has that skill";
        }
        supervisor.getSkills().add(skillOpt.get());
        supervisorRepository.save(supervisor);
        return "Skill added to the supervisor";
    }
}
