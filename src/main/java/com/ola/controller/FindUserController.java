package com.ola.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.service.EmailService;
import com.ola.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FindUserController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
	private EmailService emailService;
    
    
    // 아이디 찾는 메소드
    @PostMapping("/find-id")
    public ResponseEntity<?> findUserId(@RequestParam String name, @RequestParam String email) {
        String memberId = memberService.findMemberIdByNameAndEmail(name, email);

        if (memberId != null) {
            return ResponseEntity.ok("찾은 아이디: " + memberId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보로 등록된 아이디가 없습니다.");
        }
    }
    
    @PostMapping("/recover-password")
    public ResponseEntity<?> recoverPassword(@RequestParam String name, 
                                             @RequestParam String memberId, 
                                             @RequestParam String email, 
                                             HttpSession httpSession) {
        boolean isValidUser = memberService.validateUser(memberId, name, email);
        if (!isValidUser) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 사용자 정보입니다.");
        }

        String verificationCode = String.format("%06d", new Random().nextInt(999999));
        httpSession.setAttribute("verificationCode", verificationCode);
        emailService.sendVerificationCode(email, verificationCode);

        return ResponseEntity.ok("인증번호가 이메일로 전송되었습니다.");
    }
    
    @PostMapping("/verify-codePwd")
    public ResponseEntity<?> verifyCode(@RequestParam String verificationCode, HttpSession httpSession) {
        String storedCode = (String) httpSession.getAttribute("verificationCode");
        if (storedCode != null && storedCode.equals(verificationCode)) {
            return ResponseEntity.ok("인증번호가 정확합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호 불일치");
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String newPassword, HttpSession httpSession) {
        String userId = (String) httpSession.getAttribute("memberId"); // 세션에서 사용자 ID 가져오기
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증 실패");
        }

        boolean isUpdated = memberService.updatePassword(userId, newPassword);
        if (isUpdated) {
            return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 변경되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "비밀번호 변경 실패"));
        }
    }
   
}