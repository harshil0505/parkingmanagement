package com.Online.ParkigManagement.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Online.ParkigManagement.Repository.HistoryRepository;
import com.Online.ParkigManagement.model.DriverDetails;
import com.Online.ParkigManagement.model.History;
import com.Online.ParkigManagement.model.VehicalDetails;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public void saveHistory(DriverDetails driverDetails, VehicalDetails vehicalDetails, 
                            String parkingName, String address, 
                            String action, Long adminId) {
        History history = new History();
        history.setParkingName(parkingName);
        history.setAddress(address);
        history.setAction(action);
        history.setAdminId(adminId);
        history.setActionTime(java.time.LocalDateTime.now());
        history.setDriverDetails(driverDetails);
        history.setVehicalDetails(vehicalDetails);
        history.setFeePaidDate(java.time.LocalDate.now());

        historyRepository.save(history);
    }

    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    public List<History> getHistoryByDriverId(Long driverId) {
        return historyRepository.findByDriverDetails_DriverId(driverId);
    }

    public List<History> getHistoryByVehicalId(Long vehicalId) {
        return historyRepository.findByVehicalDetails_VehicalId(vehicalId);
    }
}
