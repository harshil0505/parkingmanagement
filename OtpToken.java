package com.Online.ParkigManagement.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "otp_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpToken {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(nullable = false)
private String email;


@Column(nullable = false)
private String otp;


@Column(nullable = false)
private Instant expiresAt;


@Column(nullable = false)
private int attempts = 0;


@Column(nullable = false)
private Instant createdAt = Instant.now();
}
