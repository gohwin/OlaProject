package com.ola.controller;

import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.dto.PasswordResetRequest;
import com.ola.service.EmailService;
import com.ola.service.MemberService;
import com.ola.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FindUserController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private UserService userService;
    
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
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        boolean result = userService.resetPassword(request.getMemberId(), request.getNewPassword());
        if (result) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("message", "비밀번호가 성공적으로 변경되었습니다.");
            }});
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "비밀번호 변경에 실패했습니다.");
            }});
        }
    }
   
}