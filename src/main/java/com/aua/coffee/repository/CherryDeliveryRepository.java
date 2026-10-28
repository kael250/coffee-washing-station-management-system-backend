package com.aua.coffee.repository;

import com.aua.coffee.domain.CherryDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CherryDeliveryRepository extends JpaRepository<CherryDelivery, UUID> {
}
