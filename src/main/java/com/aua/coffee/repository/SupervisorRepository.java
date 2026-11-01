package com.aua.coffee.repository;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.DryingTable;
import com.aua.coffee.domain.Farmer;
import com.aua.coffee.domain.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {

    @Query("""
            SELECT DISTINCT s FROM Supervisor s
            JOIN s.skills sk
            WHERE LOWER(sk.name) = LOWER(:name)
            """)
    List<Supervisor> findBySkillName(@Param("name") String name);

    @Query("""
            SELECT DISTINCT f FROM CherryDelivery c
            JOIN c.farmer f
            WHERE c.supervisor.id = :supervisorId
            """)
    List<Farmer> findFarmersBySupervisor(@Param("supervisorId") UUID supervisorId);

    List<Supervisor> findByDryingTable(DryingTable dryingTable);
}
