package org.example.izzy.service.interfaces.general;

import jakarta.validation.Valid;
import org.example.izzy.model.dto.request.general.CodeVerificationReq;
import org.example.izzy.model.dto.request.general.PhoneVerificationReq;

public interface VerifyService  {
    void sendVerificationCode(@Valid PhoneVerificationReq phoneNumber);

    boolean verifyCode(@Valid CodeVerificationReq codeVerificationReq);
}
