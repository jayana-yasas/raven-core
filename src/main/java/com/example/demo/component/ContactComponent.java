package com.example.demo.component;

import com.example.demo.dto.request.ContactDto;
import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@org.springframework.stereotype.Component
@AllArgsConstructor
public class ContactComponent {

    private final ContactRepository contactRepository;
    private final TagRepository tagRepository;

    public void deleteContact(String traceId, Long contactId) {
        contactRepository.deleteById(contactId);
    }

    public Contact updateContactEntity(String traceId, Contact contact, ContactDto contactDto) {
        contact.setEmail(contactDto.getEmail());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact.setName(contactDto.getName());
        return contactRepository.saveAndFlush(contact);
    }

    public Contact createContactEntity(String traceId, ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setEmail(contactDto.getEmail());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact.setName(contactDto.getName());
        return contactRepository.saveAndFlush(contact);
    }

    public Map<String, Object> getContactByBorrowerId(String traceId, int page, int size) {
        List<HashMap<String, Object>> loanListDetails = null;
        HashMap<String, Object> param = new HashMap<>();
        String query = "select * from contact c where 1=1 ";
        String countQuery = "select count(*) from contact c where 1=1 ";
        String sortTechnique = "desc";
        String sortBy = "c.updated_at ";

        query = query + " AND 1=1 " + " ORDER BY " + sortBy + sortTechnique;
        query = query + " LIMIT " + size + " OFFSET " + (page * size);
//        List<Object[]> contactList = (List<Object[]>) dataAccessService.readNative(query, param);
//        List<BigInteger> count = (List<BigInteger>) dataAccessService.readNative(countQuery, param);

//        loanListDetails = contactList.stream().map(q -> {
//            HashMap<String, Object> res = new HashMap<>();
//            if (q[0] != null) {
//                res.put("f1", ((BigInteger)q[0]));
//                res.put("f2", new Date(((Timestamp) q[1]).getTime()).toString());
//                res.put("f3", new Date(((Timestamp) q[2]).getTime()).toString());
//            }
//            res.put("f4", q[3]);
//            res.put("f5", q[4]);
//            res.put("f6", q[5]);
//            return res;
//        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("contactList", loanListDetails);
        response.put("currentPage", page);
//        response.put("totalItems", count.get(0));
//        response.put("totalPages", (Long.valueOf(count.get(0).toString()) /size)+1);

        return response;
    }


}
