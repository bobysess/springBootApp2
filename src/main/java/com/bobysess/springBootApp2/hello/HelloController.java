
package com.bobysess.springBootApp2.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequiredArgsConstructor
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private final HelloService helloService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;


    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return helloService.hello(authentication.getName());
    }

    @GetMapping("/event/{name}")
    public void event(@PathVariable String name) {
        logger.info("((((- Before publishing Event");
        
        applicationEventPublisher.publishEvent(MyEvent.from(name));

        logger.info("((((- After publishing Event");
    }

    public record MyEvent(String name) {

        public static MyEvent from(String name) {
            return new MyEvent(name);
        }
    }
}
