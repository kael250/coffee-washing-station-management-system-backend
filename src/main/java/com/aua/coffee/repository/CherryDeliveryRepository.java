package com.aua.coffee.repository;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CherryDeliveryRepository extends JpaRepository<CherryDelivery, UUID> {

    List<CherryDelivery> findByStatusOrderByDeliveryDateAsc(DeliveryStatus status);

    List<CherryDelivery> findByDeliveryDateBetweenOrderByDeliveryDateAsc(LocalDate start, LocalDate end);

    boolean existsBySupervisorIdAndDeliveryDateAndStatusIsNot(UUID supervisorId, LocalDate deliveryDate, DeliveryStatus status);

    @Query("""
            SELECT c.status, COUNT(c)
            FROM CherryDelivery c
            GROUP BY c.status
            """)
    List<Object[]> findStatsByStatus();

    @Modifying
    @Query("""
            UPDATE CherryDelivery c
            SET c.status = com.aua.coffee.domain.DeliveryStatus.REJECTED
            WHERE c.supervisor.id = :supervisorId
            AND c.deliveryDate = :date
            AND c.status <> com.aua.coffee.domain.DeliveryStatus.DRIED
            """)
    int cancelDay(@Param("supervisorId") UUID supervisorId, @Param("date") LocalDate date);
}
