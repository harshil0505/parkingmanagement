package com.Online.ParkigManagement.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl {

    private final JavaMailSender mailSender;
    public EmailVerificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
   public void  sendOtpEmail(String to,String otp){
  SimpleMailMessage mailMessage=new SimpleMailMessage();
    mailMessage.setTo(to);
    mailMessage.setSubject("Your OTP Code");
    mailMessage.setText("Your OTP code is: "+otp);
    mailSender.send(mailMessage);

   }
    
}
