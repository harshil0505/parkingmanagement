package com.Online.ParkigManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Online.ParkigManagement.model.AdvaceBooking;

public interface AdvanceBookingRepository  extends JpaRepository<AdvaceBooking, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find bookings by user or date range

}


