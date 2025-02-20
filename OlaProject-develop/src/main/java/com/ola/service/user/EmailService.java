package com.ola.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String code) {
        try {
            String subject = "회원가입 이메일 인증";
            String message = "귀하의 이메일 인증 코드 : " + code;

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            // 🔹 발신자 이메일을 설정 (spring.mail.username 값과 동일해야 함)
            mailMessage.setFrom("jangsh4752@naver.com"); 

            mailSender.send(mailMessage);
            System.out.println("✅ 이메일 발송 성공: " + to);
        } catch (Exception e) {
            System.err.println("❌ 이메일 발송 실패: " + e.getMessage());
        }
    }
}
