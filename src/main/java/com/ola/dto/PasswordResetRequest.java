package com.ola.dto;

import lombok.Data;

/* 비밀번호 찾기 후 재설정을 위한 DTO */ 
@Data
public class PasswordResetRequest {
    private String memberId; // 또는 memberId
    private String newPassword;

    
}