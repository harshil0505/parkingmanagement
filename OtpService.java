package com.Online.ParkigManagement.Service;

import static java.lang.System.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Online.ParkigManagement.Repository.OtpTokenRepositroy;
import com.Online.ParkigManagement.model.OtpToken;

import jakarta.transaction.Transactional;

@Service
public class OtpService {
 
    @Autowired
    private  OtpTokenRepositroy otpTokenRepositroy;

    @Autowired
    private EmailVerificationServiceImpl emailVerificationServiceimpl;

    private final Random random = new SecureRandom();
  
    private static final int OTP_LENGTH=6;

    @Value("${otp.resend.cooldown.seconds:60}")
    private int resendCooldownSeconds;

    @Value("${otp.max.attempts:5}")
     private int maxAttempts;

     @Value("${otp.expiration.minutes:5}")
     private long otpExpiryMinutes;

    private String genrateNumber(int length){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            sb.append(random.nextInt(10));
 }
 return sb.toString();

    }

    @Transactional
    public void createAndSendOtp(String email){
        otpTokenRepositroy.findTopByEmailOrderByCreatedAtDesc(email)
        .ifPresent(prev -> {
            if(Instant.now().isBefore(prev.getCreatedAt().plusSeconds(resendCooldownSeconds))){
                throw new IllegalStateException("OTP already sent. Please wait before requesting a new one.");
            }
        });

        String otp=genrateNumber(OTP_LENGTH);
        OtpToken otpToken=new OtpToken();
        otpToken.setEmail(email);
        otpToken.setOtp(otp);
        otpToken.setExpiresAt(Instant.now().plus(otpExpiryMinutes, ChronoUnit.MINUTES));
        otpToken.setCreatedAt(Instant.now());
        otpTokenRepositroy.save(otpToken);
        emailVerificationServiceimpl.sendOtpEmail(email, otp);
        out.println("OTP sent to email: "+email);

    }

    @Transactional
    public boolean verifyOtp(String email,String otp){
        OtpToken token=otpTokenRepositroy.findTopByEmailOrderByCreatedAtDesc(email)
        .orElseThrow(()->new IllegalArgumentException("No OTP found for email: "+email));

        if(token.getAttempts()>maxAttempts){
            throw new IllegalStateException("Maximum verification attempts exceeded.");
        }

        if(Instant.now().isAfter(token.getExpiresAt())){
            throw new IllegalStateException("OTP has expired. Please request a new one.");
        }

        token.setAttempts(token.getAttempts()+1);
        otpTokenRepositroy.save(token);

        if(token.getOtp().equals(otp)){
            otpTokenRepositroy.deleteByEmail(email);
            return true;
        }

        if(token.getAttempts()>=maxAttempts){
            otpTokenRepositroy.deleteByEmail(email);
            throw new IllegalStateException("Maximum verification attempts exceeded.");
        }
        return false;

    }
}