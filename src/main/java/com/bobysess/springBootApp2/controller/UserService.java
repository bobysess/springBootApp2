package com.bobysess.springBootApp2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import io.micrometer.observation.annotation.Observed;
import io.micrometer.tracing.annotation.ContinueSpan;
import io.micrometer.tracing.annotation.NewSpan;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


//@EnableAsync
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    
    public List<User> findAll () {
        return userRepository.findAll();
    }

    //@Async
    //@NewSpan
    @Observed
    public void asyncOperation () {
        logger.info("Async Operation");
    }

    @PostConstruct
    public void init() {
        userRepository.saveAll(List.of(
            new User("John", "Doe", "john.doe@example.com"),
            new User("Jane", "Smith", "jane.smith@example.com"),
            new User("Alice", "Johnson", "alice.johnson@example.com")
        ));
    }
}
