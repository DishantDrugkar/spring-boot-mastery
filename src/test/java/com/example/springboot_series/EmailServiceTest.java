package com.example.springboot_series;

import com.example.springboot_series.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail() {
        assertDoesNotThrow(() -> {
            emailService.sendEmail(
                    "dishantdrugkar4@gmail.com",
                    "Testing Java Mail Sender",
                    "Hi! This is Dishant testing Java Mail Sender."
            );
        });
    }
}
