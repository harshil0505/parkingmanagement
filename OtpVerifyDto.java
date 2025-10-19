package com.Online.ParkigManagement.Payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerifyDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String otp;
}
