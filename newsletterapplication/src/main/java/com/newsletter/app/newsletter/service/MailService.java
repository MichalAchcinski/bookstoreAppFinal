package com.newsletter.app.newsletter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public List<String> getAllEmailsFromUsers() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new org.mariadb.jdbc.Driver());
        dataSource.setUrl("jdbc:mariadb://localhost:3306/keycloak");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        String sql = "SELECT EMAIL from keycloak.user_entity where EMAIL is not null;";

        JdbcTemplate jtm = new JdbcTemplate(dataSource);
        List<Map<String, Object>> list = jtm.queryForList(sql);
        List<String> stringList = new ArrayList<>();

        for (Map map : list) {
            stringList.addAll(map.values());
        }

        return stringList;
    }

    public void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text +
                "<hr><img src='cid:bookstoreIcon' style='float:left; width:5%; height:5%'/>"
                + "<h2><p><b>BookStoreApp - Michał Achciński</b></p></h2>", isHtmlContent);
        mimeMessageHelper.addInline("bookstoreIcon", new ClassPathResource("/static/images/iconweb.png"));


        javaMailSender.send(mimeMessage);
    }
}
