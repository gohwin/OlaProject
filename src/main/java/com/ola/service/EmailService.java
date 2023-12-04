package com.ola.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ola.entity.Member;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String code) {
        String subject = "회원가입 이메일 인증";
        String message = "귀하의 이메일 인증 코드는: " + code;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}