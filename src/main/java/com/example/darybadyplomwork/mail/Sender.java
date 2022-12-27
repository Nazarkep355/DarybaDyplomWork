package com.example.darybadyplomwork.mail;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    JavaMailSender javaMailSender;

    public Sender() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        javaMailSender = context.getBean(JavaMailSender.class);
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(javaMailSender.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);

    }
    public void sendLetterAboutRegistration(String email,String password){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello, thank you for registration. Here is your generated password: ")
                .append(password);
        this.sendSimpleMessage(email,"Thank you for registration",stringBuilder.toString());
    }

    public void sendSimpleMessage(
            String[] to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(javaMailSender.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);

    }
}
