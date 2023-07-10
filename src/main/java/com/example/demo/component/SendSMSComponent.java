package com.example.demo.component;

import com.example.demo.dto.request.SmsRequestDto;
import com.example.demo.dto.response.SMSReposnse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Slf4j
@Component
@AllArgsConstructor
public class SendSMSComponent {

    private final GetCostComponent getCostComponent;

    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

    public SMSReposnse sendMessage(SmsRequestDto smsRequestDto, String traceId) {
        SMSReposnse smsReposnse = new SMSReposnse();
        double totalCost = 0;
        double numberOfPhoneNumbers = 0;
        try {
            double numberOfUnits = Math.ceil(((smsRequestDto.getSmsContent().length() / 157D)));
            for (String phoneNumber : smsRequestDto.getDestinations()) {
                log.info("Sending to {}, {}", phoneNumber, smsRequestDto.getSmsContent());
                totalCost = totalCost + getCostComponent.getByUnits(numberOfUnits, phoneNumber);
                numberOfPhoneNumbers++;
            }
            smsReposnse.setReferenceId(smsRequestDto.getReferenceId());
            smsReposnse.setTraceId(traceId);
            smsReposnse.setNumberOfPhoneNumbers(numberOfPhoneNumbers);
            smsReposnse.setNumberOfUnits(numberOfUnits);
            smsReposnse.setCost(totalCost);
        } catch (Exception ex) {
            log.error("");
        }

        return smsReposnse;
    }

    public SMSReposnse sendOtp(SmsRequestDto smsRequestDto, String traceId) {
        SMSReposnse smsReposnse = new SMSReposnse();
        double totalCost = 0;
        double numberOfPhoneNumbers = 0;
        try {
            double numberOfUnits = Math.ceil(((smsRequestDto.getSmsContent().length() / 157D)));
            for (String phoneNumber : smsRequestDto.getDestinations()) {
                log.info("Sending to {}, {}", phoneNumber, smsRequestDto.getSmsContent());
                totalCost = totalCost + getCostComponent.getByUnits(numberOfUnits, phoneNumber);
                numberOfPhoneNumbers++;
            }
            System.out.println((generateRandomDigits(5)));

            LocalDateTime a = LocalDateTime.now().plus(300L, ChronoUnit.MILLIS);
            smsReposnse.setReferenceId(smsRequestDto.getReferenceId());
            smsReposnse.setTraceId(traceId);
            smsReposnse.setNumberOfPhoneNumbers(numberOfPhoneNumbers);
            smsReposnse.setNumberOfUnits(numberOfUnits);
            smsReposnse.setCost(totalCost);

        } catch (Exception ex) {
            log.error("");
        }

        return smsReposnse;
    }

}
