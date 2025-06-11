package com.bobysess.springBootApp2.service;

import org.springframework.stereotype.Service;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@Service
public class HelloService {

    @Timed(value = "hello.timer", description = "hello timer")
    @Counted(value = "hello.counter", description = "hello counter")
    public String hello3(String name) {
        return "Hello " + name;
    }

}
