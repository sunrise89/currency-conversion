package com.sunrise.api.discovery.config;

import com.sunrise.api.discovery.client.RestTemplateApiClient;
import com.sunrise.api.discovery.model.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;

@Configuration
@EnableScheduling
@EnableAsync
public class FindFastRegistrationService {

    Logger logger = LoggerFactory.getLogger(FindFastRegistrationService.class);

    private static boolean initialRegistrationFlag;

    @Value("${find-fast.client.serviceUrl}")
    private String findFastUrl;

    @Value("${find-fast.client.registrationPath}")
    private String registrationPath;

    @Value("${find-fast.client.healthCheckPath}")
    private String healthCheckPath;

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    RestTemplateApiClient restTemplateApiClient;

    @Autowired
    Environment environment;

    @Scheduled(fixedRate = 10000)
    @Async
    public void scheduleRegistration() {
        if (isDiscoveryServerHealthy() && !initialRegistrationFlag) {
            logger.info("Attempting to register service on discovery server:: "+serviceName);
            try {
                String registrationUrl = UriComponentsBuilder.fromUriString(findFastUrl).path(registrationPath).build().toString();
                Instance appInstance = Instance.builder()
                        .host(InetAddress.getLocalHost().getHostAddress())
                        .port(Integer.valueOf(environment.getProperty("local.server.port")))
                        .serviceName(serviceName)
                        .build();
                HttpEntity<Instance> requestEntity = new HttpEntity<>(appInstance);
                restTemplateApiClient.exchangeForObject(registrationUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Object>() {
                });
            } catch(Exception e){
                //e.printStackTrace();
                logger.error("Failed to register service on discovery server");
                return;
            }
            initialRegistrationFlag = true;
            logger.info("Service registered successfully");
        }
    }

    private boolean isDiscoveryServerHealthy() {
        try {
            String healthCheckUrl = UriComponentsBuilder.fromUriString(findFastUrl).path(healthCheckPath).build().toString();
            restTemplateApiClient.exchangeForObject(healthCheckUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {});
        } catch(Exception e) {
            //e.printStackTrace();
            initialRegistrationFlag = false;
            logger.error("Discovery server health check failed, registration will be attempted again");
            return false;
        }
        return true;
    }

}
