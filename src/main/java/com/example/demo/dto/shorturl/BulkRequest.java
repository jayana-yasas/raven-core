package com.example.demo.dto.shorturl;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BulkRequest {

    private List<Link> links;
    private Boolean allowDuplicates;
    private String domain;

    @Data
    @Builder
    public static class Link{
        private String originalURL;
        private String path;
    }
}
