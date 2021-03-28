package com.newsletter.app.newsletter;

import com.newsletter.app.newsletter.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class NewsLetterScheduler {
    final MailService mailService;
    final JavaMailSender javaMailSender;

    @Scheduled(cron = "0 * * ? * *")
    @GetMapping("/sendmail")
    void sendMailNewsLetter() throws MessagingException {

        String mailSubject = "NewsLetter";
        String mailText = "Witaj :) To jest cotygodniowy newsletter!";

        for (String email : mailService.getAllEmailsFromUsers()) {
            mailService.sendMail(email, mailSubject, mailText, true);
        }
    }
}
