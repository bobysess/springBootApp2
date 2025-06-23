package com.bobysess.springBootApp2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ExternRestController {

    private final RestClient restClient;
    private final Logger logger = LoggerFactory.getLogger(ExternRestController.class);

    public ExternRestController(RestClient restClient) {
        this.restClient = restClient;
    }

    
    @GetMapping("/hello/extern")
    String scheduling() {
        logger.info("startig calling with oauth2");
        var resp = restClient.get()
                .uri("http://localhost:8081/hello")
                .attributes(clientRegistrationId("keycloak"))
                .retrieve()
                .body(String.class);
                
        logger.info("Ending calling '/hello' with oath2 with response: {}", resp);
        return "from extern rest call: " + resp;
    }
}
