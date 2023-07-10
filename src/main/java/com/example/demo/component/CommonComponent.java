package com.example.demo.component;

import com.example.demo.dto.request.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.validator.UserValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class CommonComponent {

    private final UserValidator validator;
    private final SendSMSComponent sendSMSComponent;
    private final ContactRepository contactRepository;
    private final TagRepository tagRepository;
    private final ContactTagRepository contactTagRepository;
    private final UserRepository userRepository;
    private final SegmentRepository segmentRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignTagRepository campaignTagRepository;
    private final TemplateRepository templateRepository;


    public boolean is(String email) {
        List<User> otpUser = userRepository.findAllByEmail(email);

        if (Objects.nonNull(otpUser) && otpUser.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public User getotpUser(String email) {
        List<User> otpUser = userRepository.findAllByEmail(email);

        if (Objects.nonNull(otpUser) && otpUser.size() == 1) {
            return otpUser.get(0);
        } else {
            return null;
        }
    }

    public void signUp(String traceId, SignUpDto signUpDto) {
        validator.validatePhone(traceId, signUpDto.getPhoneNumber());
        validator.validateEmail(traceId, signUpDto);
        validator.validatePassword(traceId, signUpDto);
        validator.checkAlreadyExists(traceId, signUpDto);

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(signUpDto.getPassword());
        user.setFirstName(signUpDto.getFirstName());

        userRepository.save(user);
        //send otp
        SmsRequestDto smsRequestDto = SmsRequestDto.builder()
                .destination(signUpDto.getPhoneNumber())
                .smsContent("Your otp is 1111")
                .senderMask("")
                .build();
        sendSMSComponent.sendOtp(smsRequestDto, traceId);
    }

    @Transactional
    public void deleteContact(String traceId, List<Long> contactIdList) {
        contactTagRepository.deleteByContactIdIn(contactRepository.findAllById(contactIdList));
        long a = contactRepository.deleteByIdIn(contactIdList);

        log.info("{} number of Contacts deleted", a);
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

    public void addContact(String traceId, Long userId, ContactDto contactDto) {
        contactDto.setUserId(userId);
        List<Contact> contactList = contactRepository.getListByEmailAndPhoneNumber(userId, contactDto.getEmail(), contactDto.getPhoneNumber());

        Contact contact = null;

        if (!contactList.isEmpty()) {
            contact = updateContactEntity(traceId, contactList.get(0), contactDto);
        } else {

            contact = createContactEntity(traceId, contactDto);
        }

        List<String> tags = Arrays.stream(contactDto.getTags()).toList();

        for (String tagName : tags) {
            List<Tag> tagtList = tagRepository.findAllByName(tagName);

            if (!tagtList.isEmpty()) {
                List<ContactTag> contactTagtList = contactTagRepository.findAll();

                ContactTag contactTag;
                if (!contactTagtList.isEmpty()) {
                    contactTag = contactTagtList.get(0);
                    contactTag.setContactId(contact);
                    contactTag.setTagId(tagtList.get(0));
                    contactTagRepository.save(contactTag);
                } else {
                    contactTag = new ContactTag();
                    contactTag.setContactId(contact);
                    contactTag.setTagId(tagtList.get(0));
                    contactTagRepository.save(contactTag);
                }

            } else {

                Tag tag = new Tag();
                tag.setName(tagName);
                tag = tagRepository.save(tag);

                ContactTag contactTag = new ContactTag();
                contactTag.setContactId(contact);
                contactTag.setTagId(tag);
                contactTagRepository.save(contactTag);
            }

        }

        return;

    }

    public Map<String, Object> getLoanByBorrowerId(String traceId, int page, int size) {
        List<HashMap<String, Object>> loanListDetails = null;
        String sortTechnique = "desc";
        String sortBy = "c.updated_at ";
        int offset = (page * size);

        List<Object[]> contactList = contactRepository.fetchPaginatedList(size, offset);
        List<BigInteger> count = contactRepository.fetchCount();

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

    public Map<String, Object> getContactList(String tagName, String traceId, int page, int size) {
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
//        List<BigInteger> count = contactRepository.fetchCount();
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

    public void addCampaign(String traceId, Long userId, CampaignDto campaignDto) {

        Campaign campaign = new Campaign();
        campaign.setType(campaign.getType());
        campaign.setSender(campaignDto.getSender());
        campaign.setName(campaignDto.getName());
        campaign.setTemplate(campaignDto.getTemplate());
        campaign.setIsScheduled(campaignDto.getIsScheduled());
        campaign.setScheduledDatetime(LocalDateTime.parse(campaignDto.getScheduledAt()));
        campaign = campaignRepository.save(campaign);


        List<CampaignTag> campaignTags = new ArrayList<>();

        List<Tag> tags = tagRepository.findByNameIn(Arrays.asList(campaignDto.getToTags()));
        for (Tag tag : tags) {
            CampaignTag campaignTag = new CampaignTag();
            campaignTag.setCampaignId(campaign);
            campaignTag.setTagId(tag);
            campaignTags.add(campaignTag);
        }
        campaignTagRepository.saveAll(campaignTags);
    }

    public void addTemplate(String traceId, Long userId, TemplateDto templateDto) {
        NotificationTemplate notificationTemplate;
        Optional<NotificationTemplate> templateOptional = templateRepository.findByNameAndTemplateType(templateDto.getName(), templateDto.getType());
        notificationTemplate = templateOptional.orElseGet(NotificationTemplate::new);

        notificationTemplate.setTemplateType(templateDto.getType());
        if (templateDto.getType().equalsIgnoreCase("SMS")) {
            notificationTemplate.setTemplatesSms(templateDto.getTemplate());
        } else if (templateDto.getType().equalsIgnoreCase("EMAIL")) {
            notificationTemplate.setTemplatesEmail(templateDto.getTemplate());
        }
        notificationTemplate.setName(templateDto.getName());
//        notificationTemplate.setUserId(userId);
        templateRepository.save(notificationTemplate);
    }
}
