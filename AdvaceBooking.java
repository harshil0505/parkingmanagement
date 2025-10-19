package com.Online.ParkigManagement.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "advance_booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvaceBooking {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long driverId;
    
    @NotBlank
    @Size(min = 3, max = 50, message = "DriverName must be between 3 and 50 characters")
    private String driverName;

    @NotBlank
    @Size(min = 10, max = 10, message = "DriverPhoneNumber must be between 10 nubers")
    private Integer driverPhoneNumber;
   
    @NotBlank
    private String DayName;
   
    @NotBlank
    @Size( message = "Booking time slots cannot be empty")
    private Set<LocalDateTime> bookingTimeSlots;
  

  
}
