package com.Online.ParkigManagement.Service;

public interface EmailVerificationService {

    void sendOtpEmail(String email, String otp);

}
