package com.example.demo.component;

import com.example.demo.dto.request.ContactTagsDto;
import com.example.demo.dto.response.TagDto;
import com.example.demo.dto.shorturl.BulkRequest;
import com.example.demo.dto.shorturl.BulkResponse;
import com.example.demo.entity.Contact;
import com.example.demo.entity.ContactTag;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.ContactTagRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class CommonComponent {

    private final ContactRepository contactRepository;
    private final TagRepository tagRepository;
    private final ContactTagRepository contactTagRepository;
    private final UserRepository userRepository;
    private final ContactTagComponent contactTagComponent;

    public boolean is(String email) {
        List<User> otpUser = userRepository.findAllByEmail(email);

        if (Objects.nonNull(otpUser) && otpUser.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public User getotpUser(String email) {
        List<User> userList = userRepository.findAllByEmail(email);

        if (Objects.nonNull(userList) && userList.size() == 1) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteContactTag(String traceId, Long tagId) {
        contactTagRepository.deleteByTagId(tagId);
    }

    public Map<String, Object> getContactList(String tagName, String traceId, int page, int size, boolean all) {
        List<HashMap<String, Object>> loanListDetails = null;
        String sortTechnique = "desc";
        String sortBy = "c.updated_at ";
        int offset = (page * size);
        Tag tag = tagRepository.findFirstByName(tagName).orElse(null);
        if (Objects.isNull(tag)) {
            Map<String, Object> response = new HashMap<>();
            response.put("contactList", loanListDetails);
            response.put("currentPage", page);
            response.put("totalItems", 0);
            response.put("totalPages", 1);

            return response;
        }

        List<Object[]> contactList = contactRepository.fetchPaginatedListByTagId(tag.getId(), size, offset);
        List<BigInteger> count = List.of(BigInteger.valueOf(contactList.size()));

        loanListDetails = contactList.stream().map(q -> {
            HashMap<String, Object> res = new HashMap<>();
            if (q[0] != null) {
                res.put("f1", (q[0]));
                res.put("f2", new Date(((Timestamp) q[1]).getTime()).toString());
                res.put("f3", new Date(((Timestamp) q[2]).getTime()).toString());
            }
            res.put("f4", q[3]);
            res.put("f5", q[4]);
            res.put("f6", q[5]);
            return res;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("contactList", loanListDetails);
        response.put("currentPage", page);
        response.put("totalItems", count.get(0));
        response.put("totalPages", (Long.valueOf(count.get(0).toString()) / size) + 1);

        return response;
    }

    public List<BulkResponse> updateInvoice(BulkRequest bulkRequest) throws URISyntaxException {
        String SECRET_TOKEN = "sk_V7ADkvsdVDOiQG3s";
        try {
            ResponseEntity<List<BulkResponse>> result =
                    WebClient.create()
                            .post()
                            .uri(new URI("https://api.short.io/links/bulk"))
                            .header("Authorization", SECRET_TOKEN)
                            .bodyValue(bulkRequest)
                            .retrieve()
                            .toEntityList(BulkResponse.class)
                            .block();


            if(result.getStatusCode().value() !=200){

            }else {
                List<BulkResponse> bulkResponses = result.getBody();
                return bulkResponses;
            }
        } catch (WebClientResponseException wcre) {
            log.error("Error Response Code is {} and Response Body is {}"
                    ,wcre.getRawStatusCode(), wcre.getResponseBodyAsString());
            log.error("Exception in method updateInvoice()",wcre);
            throw wcre;
        } catch (Exception ex) {
            log.error("Exception in method updateInvoice()",ex);
            throw ex;
        }
        return null;
    }


    public Map<String, Object> getContactTags(String traceId, Long userId) {

        List<ContactTag> contactTags = contactTagRepository.findByUser_Id(userId);

        List<TagDto> tagList = contactTags.stream().map(ct -> {
            TagDto a = new TagDto();
            a.setId(ct.getTagId().getId());
            a.setName(ct.tagId.getName());
            return a;
        }).distinct().toList();

        Map<String, Object> response = new HashMap<>();
        response.put("tagList", tagList);

        return response;
    }

    public ByteArrayInputStream toExcel(String searchParam, Long userId) throws IOException {
        String[] columns = {"name", "email", "phone"};
        List<Contact> contactResponseDto = contactRepository.fetchList(searchParam, userId);
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            Sheet sheet = workbook.createSheet("dormantAccountSheet");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            //Row for Header-->
            Row headerRow = sheet.createRow(0);

            //Header
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowIdx = 1;
            for (Contact dormantAccount : contactResponseDto) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dormantAccount.getName());
                row.createCell(1).setCellValue(String.valueOf(dormantAccount.getEmail()));
                row.createCell(2).setCellValue(dormantAccount.getPhoneNumber());

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public void addNewTag(String traceId, Long userId, ContactTagsDto contactDto) {
        contactDto.setUserId(userId);
        Optional<Tag> tagOptional = tagRepository.findFirstByName(contactDto.getName());
        Tag tag = null;
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        } else {
            tag = new Tag();
            tag.setName(contactDto.getName());
            tag = tagRepository.save(tag);
        }

        User user = userRepository.getById(userId);

        for (Long contactId : contactDto.getContactIds()) {
            Optional<Contact> contactOpt = contactRepository.findById(contactId);

            List<ContactTag> contactTags = contactTagRepository.findByContactId_IdAndTagId_IdAndUser_Id(contactId, tag.getId(), userId);
            if (contactTags.isEmpty() && contactOpt.isPresent()) {

                contactTagComponent.create(traceId, contactOpt.get(), tag, user);
            }
        }

    }
}
