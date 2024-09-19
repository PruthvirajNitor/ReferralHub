package com.ReferralHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private String from = "referralmanagementsystem@gmail.com";
//    private String messageText = "Hello, This is a confirmation mail";
//    private String subject = "Confirmation mail";

    @Autowired
    private JavaMailSender sendMail;

    public void sendSimpleEmail(String toEmail,String messageText, String subject){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("referralmanagementsystem@gmail.com");
        message.setTo(toEmail);
        message.setText(messageText);
        message.setSubject(subject);

        System.out.println(toEmail);
        System.out.println(messageText);
        System.out.println(subject);


        sendMail.send(message);

        System.out.println("Mail Send...");
    }
}
