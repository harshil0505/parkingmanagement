package com.Online.ParkigManagement.model;

import java.time.LocalDateTime;
import java.util.List;
    
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; // Changed from ManyToMany
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehical_details")
public class VehicalDetails {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long vehicalId;
    
    @Column(unique = true)
    private String vehicalNumber;
    
    @Getter
    @Setter
    private String vehicalType;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Long durasionOntime;
    private Double fee;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "driver_id")
    private DriverDetails driverDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicalDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> history;
}