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
        String message = "귀하의 이메일 인증 코드 : " + code;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
    
    // 비밀번호 찾을떄 인증메일 보내는 메소드
    public void sendVerificationCode(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("비밀번호 복구 인증번호");
        message.setText("인증번호: " + verificationCode);
        mailSender.send(message);
    }
}