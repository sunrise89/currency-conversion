package com.sunrise.api.discovery.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateApiClient.class);

    private RestTemplate restTemplate;

    public RestTemplateApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T exchangeForObject(String uri, HttpMethod httpMethod, HttpEntity<?> requestEntity,
                                   ParameterizedTypeReference<T> responseType) {
        LOGGER.info("Started consuming API "+uri);
        ResponseEntity<T> responseEntity = restTemplate.exchange(uri, httpMethod, requestEntity, responseType);
        LOGGER.info("API call successful with response "+ responseEntity.getBody());
        return responseEntity.getBody();
    }

}
