package com.bobysess.springBootApp2;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Runners {
    private final UserService userService; 


    @Bean
    ApplicationRunner saveSomeUsersWithService () {
        return args -> {
            userService.saveUser(new User(1L, "Müller 3", "mueller@yahoo.de", "password"));
            userService.saveUser(new User(1L, "Müller 4", "mueller@yahoo.de", "password2"));
        };
    }
}
