package com.aua.coffee.service;

import com.aua.coffee.domain.DeliveryStatus;
import com.aua.coffee.repository.CherryDeliveryRepository;
import com.aua.coffee.repository.DryingTableRepository;
import com.aua.coffee.repository.FarmerRepository;
import com.aua.coffee.repository.ParchmentLotRepository;
import com.aua.coffee.repository.SupervisorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final FarmerRepository farmerRepository;
    private final SupervisorRepository supervisorRepository;
    private final DryingTableRepository dryingTableRepository;
    private final CherryDeliveryRepository cherryDeliveryRepository;
    private final ParchmentLotRepository parchmentLotRepository;

    public DashboardService(FarmerRepository farmerRepository, SupervisorRepository supervisorRepository, DryingTableRepository dryingTableRepository, CherryDeliveryRepository cherryDeliveryRepository, ParchmentLotRepository parchmentLotRepository) {
        this.farmerRepository = farmerRepository;
        this.supervisorRepository = supervisorRepository;
        this.dryingTableRepository = dryingTableRepository;
        this.cherryDeliveryRepository = cherryDeliveryRepository;
        this.parchmentLotRepository = parchmentLotRepository;
    }

    public Map<String, Long> summary() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("farmers", farmerRepository.count());
        map.put("supervisors", supervisorRepository.count());
        map.put("freeDryingTables", (long) dryingTableRepository.findFree().size());
        map.put("deliveriesToday", cherryDeliveryRepository.countByDeliveryDate(LocalDate.now()));
        map.put("received", cherryDeliveryRepository.countByStatus(DeliveryStatus.RECEIVED));
        map.put("parchmentLots", parchmentLotRepository.count());
        return map;
    }
}
