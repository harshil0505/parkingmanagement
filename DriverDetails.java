package com.Online.ParkigManagement.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "driver_details")
public class DriverDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    @NotBlank
    @Size(min = 3, max = 50, message = "Driver name must be between 3 and 50 characters")
    private String driverName;

    @NotNull(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0, message = "Phone number must be 10 digits")
    private Long driverPhoneNumber;

  
    @JsonIgnore
    @OneToMany(mappedBy = "driverDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> history;
 
    @JsonIgnore
    @OneToMany(mappedBy = "driverDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VehicalDetails> vehicalDetails;
}