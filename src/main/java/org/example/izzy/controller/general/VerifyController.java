package org.example.izzy.controller.general;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.izzy.model.dto.request.general.CodeVerificationReq;
import org.example.izzy.model.dto.request.general.PhoneVerificationReq;
import org.example.izzy.service.interfaces.general.VerifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.izzy.constant.ApiConstant.*;

@RestController
@RequestMapping(API + V1 + VERIFY)
@RequiredArgsConstructor
public class VerifyController {

    private final VerifyService verifyService;

    @PostMapping(SEND)
    public ResponseEntity<?> sendVerificationCode(@Valid @RequestBody PhoneVerificationReq phoneVerificationReq) {
        verifyService.sendVerificationCode(phoneVerificationReq);
        return ResponseEntity.ok("Verification code sent.");
    }

    @PostMapping(CHECK)
    public ResponseEntity<?> checkVerificationCode(@Valid @RequestBody CodeVerificationReq codeVerificationReq) {
        boolean isValid = verifyService.verifyCode(codeVerificationReq);
        return isValid
                ? ResponseEntity.ok("Phone Number verified successfully.")
                : ResponseEntity.badRequest().body("Invalid Verification code or Phone number.");
    }

}
