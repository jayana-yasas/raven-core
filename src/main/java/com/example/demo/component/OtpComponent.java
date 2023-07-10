package com.example.demo.component;

import com.example.demo.dto.request.OtpRequestDto;
import com.example.demo.dto.response.OTPResponse;
import com.example.demo.entity.OtpApplication;
import com.example.demo.repository.OtpApplicationRepository;
import com.example.demo.util.Common;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class OtpComponent {

    private final GetCostComponent getCostComponent;
    private final OtpApplicationRepository otpApplicationRepository;

    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

    public static String generateBody(String template, HashMap<String, String> params) {

        for (String placeHolder : Common.OTP_TEMPLATE_PLACEHOLDERS) {
            if (template.contains(placeHolder)) {
                template = template.replace(placeHolder, params.get(placeHolder));
            }
        }

        return template;
    }

    public OTPResponse sendOtp(OtpRequestDto otpRequestDto, String traceId) {
        OTPResponse otpResponse = new OTPResponse();
        // load configs
        // load template
        // send otp
        try {

            OtpApplication otpApplication = otpApplicationRepository.findById(2L).get();

            HashMap<String, String> params = new HashMap<>();
            params.put("<otp>", "12342");
            params.put("<otp_for>", "Quath");
            String message = generateBody(otpApplication.getNotificationTemplate().getTemplatesSms(), params);
            log.info("message : {}", message);
            log.info("{}", otpApplication);
        } catch (Exception ex) {
            log.error("");
        }

        otpResponse.setTraceId(traceId);
        otpResponse.setReferenceId(UUID.randomUUID().toString());
        return otpResponse;
    }

}
