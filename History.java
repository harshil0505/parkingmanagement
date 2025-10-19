package com.Online.ParkigManagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private String parkingName;
    private String address;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "driverId")
    private DriverDetails driverDetails;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vehical_id", referencedColumnName = "vehicalId")
    private VehicalDetails vehicalDetails;

    private LocalDate feePaidDate;
    private String action;
    private Long adminId;

    private LocalDateTime actionTime = LocalDateTime.now();
}