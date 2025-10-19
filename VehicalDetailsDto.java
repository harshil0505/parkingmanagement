package com.Online.ParkigManagement.Payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicalDetailsDto {
     
     private Long vehicalId;
     private String vehicalNumber;
     private String vehicalType;
     private LocalDateTime entryTime;
     private LocalDateTime exitTime;
     private Long durasionOntime;
     private Double fee;
     private Long driverId;
}
