package com.Online.ParkigManagement.Payload;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceBookingDto {
    private Long driverId;
    private String driverName;
    private String driverPhoneNumber;
    private String DayName;
    private Set<LocalDateTime> bookingTimeSlots;
  

}
