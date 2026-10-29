package com.aua.coffee.repository;

import com.aua.coffee.domain.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, UUID> {

    List<Farmer> findByVillageIgnoreCaseOrderByLastNameAsc(String village);
}
