package com.bobysess.springBootApp2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class UserExternController {

    private final Logger logger = LoggerFactory.getLogger(UserExternController.class);
    private final RestClient restClient;

    public UserExternController (RestClient.Builder builder) {
        restClient = builder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("/users/rest")
    public List<User> getUsersRest() {
        logger.info("making extern call to fecth users");
        return restClient
            .get()
            .uri("/users")
            .retrieve()
            .toEntity(new ParameterizedTypeReference<List<User>>() {})
            .getBody();
    }
}
