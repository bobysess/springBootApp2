package com.bobysess.springBootApp2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.beans.BeanProperty;
import java.util.HashMap;
import java.util.Map;

import org.javers.spring.annotation.JaversAuditable;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    private final UserRepository userRepository; 

    @JaversAuditable
    public void saveUser (User user) {
        users.putIfAbsent(user.id, user);
    }

    @Bean
    ApplicationRunner saveSomeUsers () {
        return args -> {
            userRepository.save(new User(1L, "Müller", "mueller@yahoo.de", "password"));
            userRepository.save(new User(1L, "Müller 2", "mueller@yahoo.de", "password2"));
        };
    }
}
