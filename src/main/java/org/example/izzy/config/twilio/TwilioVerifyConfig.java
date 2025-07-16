package org.example.izzy.config.twilio;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TwilioVerifyConfig {

    @Value("${twilio.account.sid}")
    private String smsAccountSID;

    @Value("${twilio.auth.token}")
    private String smsAccountToken;


    @PostConstruct
    public void init() {
        Twilio.init(smsAccountSID, smsAccountToken);
    }
}
