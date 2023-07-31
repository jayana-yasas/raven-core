package com.example.demo.component;

import com.example.demo.dto.request.CampaignDto;
import com.example.demo.dto.shorturl.BulkRequest;
import com.example.demo.dto.shorturl.BulkResponse;
import com.example.demo.dto.shorturl.ShortUrlDetails;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@AllArgsConstructor
public class CreateCampaignComponent {

    private final CampaignRepository campaignRepository;
    private final ContactTagRepository contactTagRepository;
    private final CampaignUrlsRepository campaignUrlsRepository;
    private final ShortUrlInfoRepository shortUrlInfoRepository;
    private final CampaignInfoRepository campaignInfoRepository;
    private final WebClientComponent webClientComponent;

    private Campaign mapTo(Long userId, CampaignDto campaignDto){
        Campaign campaign = new Campaign();
        campaign.setType(campaignDto.getType());
        campaign.setSender(campaignDto.getSender());
        campaign.setName(campaignDto.getName());

        campaign.setTemplate(campaignDto.getContent().getTemplate());
        campaign.setPageCount(campaignDto.getContent().getPageCount());
        campaign.setCharacterCount(campaignDto.getContent().getCharacterCount());
        campaign.setHasUnicode(campaignDto.getContent().getHasUnicode());

        campaign.setIsScheduled(campaignDto.getIsScheduled());
        campaign.setScheduledDatetime(LocalDateTime.parse(campaignDto.getScheduledAt()));
        campaign.setUserId(userId);

        return campaign;
    }

    private List<CampaignUrls> mapToList(CampaignDto campaignDto){
        List<CampaignUrls> campaignUrlsList = new ArrayList<>();
        for (CampaignDto.UrlsForShort urlsForShort : campaignDto.getContent().getUrlsForShort()) {
            CampaignUrls campaignUrls = new CampaignUrls();
            campaignUrls.setCampaignId(campaignDto.getCampaignId());
            campaignUrls.setParam(urlsForShort.getKey());
            campaignUrls.setOriginalURL(urlsForShort.getOriginalURL());
            campaignUrlsList.add(campaignUrls);
        }
        return campaignUrlsList;
    }

    private List<String> numberByTags(List<ContactTag> contactTags){
        List<String> toSendNumbers = new ArrayList<>();
        for (ContactTag contactTag : contactTags) {
            if(Objects.nonNull(contactTag.getContactId().getPhoneNumber())){
                toSendNumbers.add(contactTag.getContactId().getPhoneNumber());
            }
        }
        return toSendNumbers;
    }


    public void create(String traceId, Long userId, CampaignDto campaignDto) throws URISyntaxException {

//        -----------Save Campaign entity : start
        Campaign campaign = mapTo(userId, campaignDto);
        campaign = campaignRepository.save(campaign);

//        -----------Save Campaign entity : end
        campaignDto.setCampaignId(campaign.getId());

        List<CampaignUrls> campaignUrlsList = mapToList(campaignDto);

        campaignUrlsRepository.saveAll(campaignUrlsList);

//        -----------Get all numbers to send : start
        List<ContactTag> contactTags = contactTagRepository.findByTagId_IdIn(Arrays.asList(campaignDto.getToTags()));

        List<String> toSendNumbers = numberByTags(contactTags);

        toSendNumbers.addAll(Arrays.asList(campaignDto.getToNumbers()));
//        -----------Get all numbers to send : end

//        -----------Set template list against mobile : start
        List<BulkRequest.Link> links = new ArrayList<>();

        Map<String, String> mobileTemplateMap = new HashMap<>();

        for (String toSentNumber : toSendNumbers) {

            String template = campaignDto.getContent().getTemplate();

            for (CampaignDto.UrlsForShort url: campaignDto.getContent().getUrlsForShort()) {

                final String randomCode = UUID.randomUUID().toString().substring(0, 7);

                BulkRequest.Link link = BulkRequest.Link.builder().path(randomCode).originalURL(url.getOriginalURL()).build();
                links.add(link);

                ShortUrlDetails shortUrlDetails =new ShortUrlDetails();
                shortUrlDetails.setOriginalURL(url.getOriginalURL());
                template = template.replace("#"+ url.getKey() + "#", "#" +randomCode+ "#");
            }
            mobileTemplateMap.put(toSentNumber, template);
        }

        BulkRequest bulkRequest = BulkRequest.builder()
                .allowDuplicates(true)
                .domain("af40.short.gy")
                .links(links)
                .build();

        List<BulkResponse> bulkResponses = webClientComponent.bulkCreateURLs(bulkRequest);

        Map<String, String> keyShortUrlMap= new HashMap<>();
        Map<String, String> keyLinkIdlMap= new HashMap<>();
        for (BulkResponse bulkResponse : bulkResponses) {
            keyShortUrlMap.put( "#" +bulkResponse.getPath()+ "#", bulkResponse.getShortURL());
            keyLinkIdlMap.put( "#" +bulkResponse.getPath()+ "#", bulkResponse.getIdString());
        }

        for (String toSentNumber : toSendNumbers) {
            String msgWithShortLinks = mobileTemplateMap.get(toSentNumber);
            for (Map.Entry<String,String> entry : keyShortUrlMap.entrySet()) {
                if(msgWithShortLinks.contains(entry.getKey())){
                    msgWithShortLinks = msgWithShortLinks.replace(entry.getKey(), keyShortUrlMap.get(entry.getKey()));

                    ShortUrlInfo shortUrlInfo = new ShortUrlInfo();
                    shortUrlInfo.setCampaignId(campaign.getId());
                    shortUrlInfo.setPath(entry.getKey());
                    shortUrlInfo.setShortUrl(entry.getValue());
                    shortUrlInfo.setPhoneNumber(toSentNumber);
                    shortUrlInfo.setIdString(keyLinkIdlMap.get(entry.getKey()));

                    shortUrlInfoRepository.save(shortUrlInfo);
                }
            }

            CampaignInfo campaignInfo = new CampaignInfo();
            campaignInfo.setCampaignId(campaign.getId());
            campaignInfo.setPhoneNumber(toSentNumber);
            campaignInfo.setMessage(msgWithShortLinks);
            campaignInfo.setStatus("SENT");
            campaignInfoRepository.save(campaignInfo);
            System.out.println(toSentNumber +  " < " + msgWithShortLinks);
        }
    }

}
