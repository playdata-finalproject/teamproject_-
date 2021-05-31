package com.finalproject.shelter.business.mail;


import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
