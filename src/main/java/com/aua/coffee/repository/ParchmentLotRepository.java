package com.aua.coffee.repository;

import com.aua.coffee.domain.CherryDelivery;
import com.aua.coffee.domain.ParchmentLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParchmentLotRepository extends JpaRepository<ParchmentLot, UUID> {

    boolean existsByCherryDelivery(CherryDelivery cherryDelivery);

    @Query("""
            SELECT p FROM ParchmentLot p
            WHERE p.cherryDelivery.farmer.farmerCode = :farmerCode
            ORDER BY p.recordedDate DESC
            """)
    List<ParchmentLot> findByFarmerCode(@Param("farmerCode") String farmerCode);
}
