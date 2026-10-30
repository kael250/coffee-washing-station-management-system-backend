package com.aua.coffee.repository;

import com.aua.coffee.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {

    @Query("""
            SELECT s FROM Skill s
            WHERE s.supervisors IS EMPTY
            """)
    List<Skill> findUnused();
}
