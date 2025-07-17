package org.example.izzy.service.interfaces.general.auth;

import jakarta.validation.Valid;
import org.example.izzy.model.dto.request.general.auth.CodeVerificationReq;
import org.example.izzy.model.dto.request.general.auth.PhoneVerificationReq;

public interface VerifyService  {
    void sendVerificationCode(@Valid PhoneVerificationReq phoneNumber);

    boolean verifyCode(@Valid CodeVerificationReq codeVerificationReq);
}
