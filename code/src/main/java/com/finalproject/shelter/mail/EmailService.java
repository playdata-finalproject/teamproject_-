package com.finalproject.shelter.mail;


import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
