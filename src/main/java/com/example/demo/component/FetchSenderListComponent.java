package com.example.demo.component;

import com.example.demo.component.steps.GetSenderListCount;
import com.example.demo.dto.response.SenderResponseDto;
import com.example.demo.entity.Sender;
import com.example.demo.repository.SenderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class FetchSenderListComponent {
    private final SenderRepository senderRepository;

    private final GetSenderListCount getSenderListCount;

    public Map<String, Object> getAllSenderList(String traceId, int page, int size, String senderType, Long userId) {

        int offset = (page * size);

        List<Sender> senderList = senderRepository.fetchPaginatedList(senderType, size, offset, userId);
        BigInteger count = getSenderListCount.bySearchParam(traceId, senderType, userId);

        List<SenderResponseDto> senderResponseList = senderList.stream().map(s -> SenderResponseDto.builder()
                .id(s.getId())
                .senderName(s.getSenderName())
                .status(s.getStatus())
                .build()).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("senderList", senderResponseList);
        response.put("currentPage", page);
        response.put("totalItems", count);
        response.put("totalPages", (Long.parseLong(count.toString()) / size) + 1);

        return response;
    }

}
