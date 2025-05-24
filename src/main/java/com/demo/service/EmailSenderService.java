package com.demo.service;

import com.demo.POJO.Email;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

//    public void sendEmail(Email email) throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//        messageHelper.setTo(email.getRecipient());
//        messageHelper.setSubject(email.getSubject());
//        messageHelper.setText(email.getText());
//
//        // Send the email
//        javaMailSender.send(mimeMessage);
//    }
public void sendEmail(MimeMessage mimeMessage) {
    javaMailSender.send(mimeMessage);
}

    public MimeMessage createMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    public void sendEmailWithAttachment(String to, String subject, String text, File file) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            helper.addAttachment(file.getName(), file);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email with attachment: " + e.getMessage());
        }
    }

}
