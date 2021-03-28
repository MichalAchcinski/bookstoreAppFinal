package pl.achcinski.bookstore.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
        mimeMessageHelper.addInline("bookstoreIcon", new ClassPathResource("/static/images/iconweb.png"));


        javaMailSender.send(mimeMessage);
    }

}
