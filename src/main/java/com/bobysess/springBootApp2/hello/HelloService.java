package com.bobysess.springBootApp2.hello;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import com.bobysess.springBootApp2.hello.HelloController.MyEvent;


@Service
@EnableAsync
//@EnableMethodSecurity
public class HelloService {
     
    private Logger logger = LoggerFactory.getLogger(HelloService.class);


    //@PreAuthorize("hasAuthority('USER')")
    public String hello (String name) {
        return "Helloworld " + name + " !";
    }

    @EventListener
    void on (MyEvent event) {
        logger.info("-))) Starting Handling Event");
        
        try {
            Thread.sleep(5_000L);
        } catch (Exception e) {
            e.printStackTrace();
        }   

        logger.info("-))) Finishing Handling Event");
    }    
}
