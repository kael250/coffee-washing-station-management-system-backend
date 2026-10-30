package com.aua.coffee.repository;

import com.aua.coffee.domain.DryingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DryingTableRepository extends JpaRepository<DryingTable, UUID> {

    @Query("""
            SELECT l FROM DryingTable l
            WHERE NOT EXISTS (
                SELECT s FROM Supervisor s
                WHERE s.dryingTable = l
            )
            ORDER BY l.tableNumber ASC
            """)
    List<DryingTable> findFree();
}
