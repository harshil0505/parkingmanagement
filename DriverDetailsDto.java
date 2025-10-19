package com.Online.ParkigManagement.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 @Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDetailsDto {
   
    private Long driverId;
    private String driverName;
    private Long driverPhoneNumber;
    
}
