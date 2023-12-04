package com.ola.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ola.member.service.MemberService;

@RestController
public class UserController {

    private MemberService memberService;

    @Autowired
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
}