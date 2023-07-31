package com.example.demo.component;

import com.example.demo.component.steps.CheckSenderExists;
import com.example.demo.component.steps.SaveSender;
import com.example.demo.dto.request.SenderDto;
import com.example.demo.entity.Sender;
import com.example.demo.exception.SenderException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AddSenderComponent {

    private final CheckSenderExists checkSenderExists;
    private final SaveSender saveSender;


    private Sender mapTo(SenderDto senderDto) {
        Sender sender = new Sender();
        sender.setStatus("PENDING");
        sender.setSenderType(senderDto.getSenderType());
        sender.setSenderName(senderDto.getSenderName());
        sender.setSenderPhone(senderDto.getSenderPhone());
        sender.setSenderEmail(senderDto.getSendEmail());
        sender.setUserId(senderDto.getUserId());
        sender.setIsIddEnabled(senderDto.getIddEnabled());
        sender.setPurpose(senderDto.getPurpose());
        sender.setSendingAsType(senderDto.getSendingAs().getSendingAsType());

        if(senderDto.getSendingAs().getSendingAsType().equalsIgnoreCase("BUSINESS")){
            sender.setBusinessLegalName(senderDto.getSendingAs().getBusiness().getBusinessLegalName());
            sender.setBusinessType(senderDto.getSendingAs().getBusiness().getBusinessType());
            sender.setAuthorizedPersonName(senderDto.getSendingAs().getBusiness().getAuthorizedPersonName());
            sender.setAuthorizedPersonDesignation(senderDto.getSendingAs().getBusiness().getAuthorizedPersonDesignation());
            sender.setAuthorizedPersonDesignation(senderDto.getSendingAs().getBusiness().getAuthorizedPersonDesignation());
            sender.setLegalDocumentType(senderDto.getSendingAs().getBusiness().getLegalDocumentType());
            sender.setLegalDocumentId(senderDto.getSendingAs().getBusiness().getLegalDocumentId());
        }
        if(senderDto.getSendingAs().getSendingAsType().equalsIgnoreCase("INDIVIDUAL")){
            sender.setFullName(senderDto.getSendingAs().getIndividual().getFullName());
            sender.setLegalDocumentType(senderDto.getSendingAs().getIndividual().getLegalDocumentType());
            sender.setLegalDocumentId(senderDto.getSendingAs().getIndividual().getLegalDocumentId());
        }

        return sender;
    }


    public void saveOrUpdate(String traceId, Long userId, SenderDto senderDto) {

        senderDto.setUserId(userId);
        boolean contactExistsForUser = checkSenderExists.check(traceId, userId, senderDto.getSenderName());

        Sender sender;
        if (contactExistsForUser) {
//            sender = checkSenderExists.get(traceId, userId, senderDto.getSenderName());
            throw new SenderException("Sender name already exists, Can not have multiple same sender names");
        } else {
            sender = mapTo(senderDto);
        }

        saveSender.saveOrUpdate(traceId, sender);
    }

}
