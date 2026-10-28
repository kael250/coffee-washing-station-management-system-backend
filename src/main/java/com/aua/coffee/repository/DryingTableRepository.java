package com.aua.coffee.repository;

import com.aua.coffee.domain.DryingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DryingTableRepository extends JpaRepository<DryingTable, UUID> {
}
