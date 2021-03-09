package ru.edjll.cinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;


    public void send(String emailTo, String subject, String title, String body) {

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setSubject(subject);

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setText("<!DOCTYPE html> <html lang=\"rus\"> <head> <meta charset=\"UTF-8\"> <title>edjll</title> <style> body{ background-color: rgb(10, 11, 24); } table{ background-color: #20202a; border: 1px solid #181924; margin: auto; border-radius: .25rem; width: 50%; color: #e1e1e1; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; font-weight: 400; padding: 1rem; } .logo { font-size: 2rem; padding: .5rem 1rem; } a { color: #e1e1e1; text-decoration: none; } .t1{ text-align: center; color: white; font-size: 1.3rem; } .title { font-size: 1.5rem; color: white; } ul{ font-size: 20px; color: white; } .img1{ text-align: center; background-color: black; } </style> </head> <body> <table> <tr> <th class=\"logo\" colspan=\"3\"><a href=\"http://localhost:8080/\" target=\"_blank\">edjll</th> </tr> <tr class=\"t1\"> <td colspan=\"3\" class=\"title\">" + title + "</td> </tr> <tr> <td colspan=\"3\">" + body + "</td> </tr> <tr class=\"t1\"> <td colspan=\"3\"><a href=\"http://localhost:8080/\" target=\"_blank\">edjll</a></td> </tr> </table> </body> </html>", true);
        };

        mailSender.send(mimeMessagePreparator);
    }
}