package com.finalproject.shelter.businessLayer.mail;


import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
