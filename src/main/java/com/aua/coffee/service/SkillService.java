package com.aua.coffee.service;

import com.aua.coffee.domain.Skill;
import com.aua.coffee.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public String save(Skill skill) {
        skillRepository.save(skill);
        return "Skill saved successfully";
    }

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Optional<Skill> findById(UUID id) {
        return skillRepository.findById(id);
    }

    public Optional<Skill> update(UUID id, Skill skill) {
        return skillRepository.findById(id).map(existing -> {
            existing.setName(skill.getName());
            return skillRepository.save(existing);
        });
    }

    public String delete(UUID id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return "Skill deleted successfully";
        }
        return null;
    }
}
