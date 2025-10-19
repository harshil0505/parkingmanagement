package com.Online.ParkigManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Online.ParkigManagement.model.VehicalDetails;

public interface VehicalDetailsRepository extends JpaRepository<VehicalDetails,Long> {

    @Query("select v from VehicalDetails v where v.vehicalNumber = :vehicalNumber")
    Optional<VehicalDetails> findByVehicalNumber(String vehicalNumber);

    @Query("SELECT v FROM VehicalDetails v WHERE v.vehicalNumber = :vehicalNumber AND v.exitTime IS NULL ORDER BY v.entryTime DESC")
Optional<VehicalDetails> findLatestActiveEntry(@Param("vehicalNumber") String vehicalNumber);

}
