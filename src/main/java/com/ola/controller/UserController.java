package com.ola.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ola.dto.PasswordResetRequest;
import com.ola.service.member.MemberService;
import com.ola.service.user.UserService;

@RestController
public class UserController {

	@Autowired
    private MemberService memberService;
	@Autowired
	private UserService userService;

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/checkIdAvailability")
    public ResponseEntity<Map<String, Boolean>> checkIdAvailability(@RequestParam String memberId) {
        boolean isAvailable = !memberService.isMemberIdExists(memberId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }
    
    // 새로운 비밀번호 설정하는 단계
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestParam String newPassword, HttpSession httpSession) {
//        // 사용자 식별 정보 (예: 이메일, 아이디) 가져오기
//        String userId = (String) httpSession.getAttribute("memberId");
//
//        // 비밀번호 업데이트 로직
//        boolean isUpdated = memberService.updatePassword(userId, newPassword);
//        if (isUpdated) {
//            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경 실패");
//        }
//    }
    
   
}
