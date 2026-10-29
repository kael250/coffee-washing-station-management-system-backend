package com.aua.coffee.repository;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CherryDeliveryRepository extends JpaRepository<CherryDelivery, UUID> {

    List<CherryDelivery> findByStatusOrderByDeliveryDateAsc(DeliveryStatus status);

    List<CherryDelivery> findByDeliveryDateBetweenOrderByDeliveryDateAsc(LocalDate start, LocalDate end);
}
