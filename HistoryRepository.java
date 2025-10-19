package com.Online.ParkigManagement.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Online.ParkigManagement.model.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByDriverDetails_DriverId(Long driverId);
    List<History> findByVehicalDetails_VehicalId(Long vehicalId);
}
