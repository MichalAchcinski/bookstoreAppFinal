package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    final JavaMailSender javaMailSender;
    final TemplateEngine templateEngine;

    public void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text +
                "<hr><img src='cid:bookstoreIcon' style='float:left; width:5%; height:5%'/>"
                + "<h2><p><b>BookStoreApp - Michał Achciński</b></p></h2>", isHtmlContent);
        mimeMessageHelper.addInline("bookstoreIcon", new ClassPathResource("iconweb.png"));


        javaMailSender.send(mimeMessage);
    }


}
