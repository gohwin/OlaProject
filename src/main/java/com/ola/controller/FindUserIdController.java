package com.ola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.member.service.MemberService;

@Controller
public class FindUserIdController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/find-id")
    public ResponseEntity<?> findUserId(@RequestParam String name, @RequestParam String email) {
        String memberId = memberService.findMemberIdByNameAndEmail(name, email);

        if (memberId != null) {
            return ResponseEntity.ok("찾은 아이디: " + memberId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보로 등록된 아이디가 없습니다.");
        }
    }
}