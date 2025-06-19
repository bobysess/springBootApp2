package com.bobysess.springBootApp2.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private Logger logger = LoggerFactory.getLogger(UserApiController.class); 
    private final UserService userService; 
    
    //@Observed
    @GetMapping("/users")
    public List<User> getUsers() {
        logger.info("starting fetching Users data");
        userService.asyncOperation();
        return userService.findAll();       
    } 
}

