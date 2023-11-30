package com.ola.controller;

import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sms")
public class SMSController {

    private final DefaultMessageService messageService;

    public SMSController(DefaultMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/send")
    public String sendSMS() {
        try {
            String phone = "";
            String verificationCode = generateVerificationCode();

            Message message = new Message();
            message.setFrom("01058773022");
//            message.setTo(No);
            message.setText("테스트문자 인증번호 4자리 [" + verificationCode + "]");

            messageService.send(message);
            System.out.println("문자발송확인");
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
            // 실패한 경우에 대한 처리 (예: 에러 페이지로 이동)
            return "redirect:/error?message=SMS%20Sending%20Failed";
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            // 일반적인 예외 처리 (예: 에러 페이지로 이동)
            return "redirect:/error?message=Unknown%20Error";
        }
        return "redirect:/member/join";
    }

    private String generateVerificationCode() {
        // 인증번호 생성 로직 구현 (예: 랜덤한 4자리 숫자 생성)
        return "1234"; // 실제로는 동적으로 생성된 값을 사용해야 합니다.
    }
}