package com.example.demo.component;

import com.example.demo.component.steps.CheckSenderExists;
import com.example.demo.entity.Sender;
import com.example.demo.exception.SenderException;
import com.example.demo.repository.SenderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class DeleteSenderComponent {
    private final SenderRepository senderRepository;
    private final CheckSenderExists checkSenderExists;
    @Transactional
    public void deleteSender(String traceId, Long userId, Long senderId) {
        boolean exists = checkSenderExists.checkById(traceId, userId, senderId);
        if(exists){
            Sender sender = checkSenderExists.getById(traceId, userId, senderId);
            sender.setStatus("DELETED");
            //        senderRepository.deleteById(senderId);
        }else {
            throw new SenderException("Sender Not Found");
        }

    }

}
