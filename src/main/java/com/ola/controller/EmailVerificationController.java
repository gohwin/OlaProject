package com.ola.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ola.service.EmailService;
import com.ola.service.VerificationCodeService;

@RestController
public class EmailVerificationController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/send-verification-code")
    public ResponseEntity<?> sendVerificationCode(@RequestParam String email) {
        String verificationCode = generateVerificationCode();
        verificationCodeService.saveVerificationCode(email, verificationCode);

        emailService.sendVerificationEmail(email, verificationCode);

        return ResponseEntity.ok("인증번호가 발송되었습니다.");
    }

    @GetMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        String savedCode = verificationCodeService.getVerificationCode(email);
        if (savedCode != null && savedCode.equals(code)) {
            verificationCodeService.removeVerificationCode(email);
            return ResponseEntity.ok(Map.of("message", "인증 성공"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "인증 실패"));
        }
    }

    // 6자리 인증번호 생성
    public String generateVerificationCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}