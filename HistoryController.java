package com.Online.ParkigManagement.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Online.ParkigManagement.Service.HistoryService;
import com.Online.ParkigManagement.model.History;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

   


    @PostMapping("/save")
    public ResponseEntity<String> saveNewHistory(@RequestBody History request) {
        try {
            
            historyService.saveHistory(
                request.getDriverDetails(),
                request.getVehicalDetails(),
                request.getParkingName(),
                request.getAddress(),
                request.getAction(),
                request.getAdminId()
            );
            return new ResponseEntity<>("History saved successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception
            return new ResponseEntity<>("Failed to save history: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 
    @GetMapping
    public ResponseEntity<List<History>> getAllHistory() {
        List<History> history = historyService.getAllHistory();
        return ResponseEntity.ok(history);
    }

   
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<History>> getHistoryByDriverId(@PathVariable Long driverId) {
        List<History> history = historyService.getHistoryByDriverId(driverId);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(history);
    }

   
    
    @GetMapping("/vehicle/{vehicalId}")
    public ResponseEntity<List<History>> getHistoryByVehicalId(@PathVariable Long vehicalId) {
        List<History> history = historyService.getHistoryByVehicalId(vehicalId);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(history);
    }
}