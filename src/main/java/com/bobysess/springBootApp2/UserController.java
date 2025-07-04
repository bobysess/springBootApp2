package com.bobysess.springBootApp2;

import org.springframework.web.bind.annotation.RestController;

import com.bobysess.springBootApp2.UserService.User;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@EnableCaching
@RestController
public class UserController {
    
    private final UserService userService; 

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }
    
    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.users();
    }

    @GetMapping("/user/{id}/evict")
    public void evict(@PathVariable long id) {
        userService.evict(id);
    }

    @GetMapping("/user/evict")
    public void evictAll() {
        userService.evictAll();
    }
}
