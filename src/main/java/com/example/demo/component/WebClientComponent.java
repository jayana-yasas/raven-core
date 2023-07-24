package com.example.demo.component;

import com.example.demo.dto.shorturl.BulkRequest;
import com.example.demo.dto.shorturl.BulkResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class WebClientComponent {

    public List<BulkResponse> bulkCreateURLs(BulkRequest bulkRequest) throws URISyntaxException {
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


            if (result.getStatusCode().value() != 200) {

            } else {
                List<BulkResponse> bulkResponses = result.getBody();
                return bulkResponses;
            }
        } catch (WebClientResponseException wcre) {
            log.error("Error Response Code is {} and Response Body is {}"
                    , wcre.getRawStatusCode(), wcre.getResponseBodyAsString());
            log.error("Exception in method updateInvoice()", wcre);
            throw wcre;
        } catch (Exception ex) {
            log.error("Exception in method updateInvoice()", ex);
            throw ex;
        }
        return null;
    }

}
