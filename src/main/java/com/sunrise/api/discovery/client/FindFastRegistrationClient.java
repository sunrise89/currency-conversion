package com.sunrise.api.discovery.client;

import com.sunrise.api.discovery.config.FindFastRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FindFastRegistrationClient {

    Logger logger = LoggerFactory.getLogger(FindFastRegistrationClient.class);

    @Autowired
    FindFastRegistrationService findFastRegistrationService;

    @EventListener
    @Async
    public void registerApplicationOnDiscoveryServer(ApplicationReadyEvent applicationReadyEvent) {
        findFastRegistrationService.scheduleRegistration();
    }

}
