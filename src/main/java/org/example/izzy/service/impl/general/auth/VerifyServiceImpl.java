package org.example.izzy.service.impl.general.auth;

import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.izzy.model.dto.request.general.auth.CodeVerificationReq;
import org.example.izzy.model.dto.request.general.auth.PhoneVerificationReq;
import org.example.izzy.service.interfaces.general.auth.VerifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    @Value("${twilio.service.sid}")
    private String smsServiceSID;


    public void sendVerificationCode(@Valid PhoneVerificationReq phoneNumber) {
        Verification.creator(
                smsServiceSID,
                phoneNumber.phoneNumber(),
                "sms"  // or "call" if you want voice OTP
        ).create();
    }


    public boolean verifyCode(@Valid CodeVerificationReq codeVerificationReq) {
        VerificationCheck verificationCheck = VerificationCheck.creator(smsServiceSID)
                .setTo(codeVerificationReq.phoneNumber())
                .setCode(codeVerificationReq.code())
                .create();

        return "approved".equals(verificationCheck.getStatus());
    }

}
