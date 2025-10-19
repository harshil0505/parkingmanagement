package com.Online.ParkigManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Online.ParkigManagement.model.OtpToken;

public interface OtpTokenRepositroy extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken>  findTopByEmailOrderByCreatedAtDesc(String email);
    
    void deleteByEmail(String email);
}
