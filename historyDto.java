package com.Online.ParkigManagement.Payload;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class historyDto {
  private Long historyId;
    private String parkingname;
    private String Address;
    private DriverDetailsDto driverId;
    private VehicalDetailsDto vehicleNumber;
    private VehicalDetailsDto fee;
    private LocalDate  FeePaidDate;
    

    private LocalDateTime actionTime = LocalDateTime.now();
}
