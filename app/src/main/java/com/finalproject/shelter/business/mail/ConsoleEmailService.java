package com.finalproject.shelter.business.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@Component
public class ConsoleEmailService implements EmailService {
    @Override
    public void sendEmail(EmailMessage emailMessage) {
        log.info("ent email : {}", emailMessage.getMessage());
    }
}