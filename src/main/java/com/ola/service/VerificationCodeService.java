package com.ola.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    private final Map<String, String> verificationCodes = new HashMap<>();

    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }

    public String getVerificationCode(String email) {
        return verificationCodes.get(email);
    }

    public void removeVerificationCode(String email) {
        verificationCodes.remove(email);
    }
    public String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}

