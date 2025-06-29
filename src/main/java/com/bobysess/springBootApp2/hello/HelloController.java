package com.bobysess.springBootApp2.hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelloController {
    
    @GetMapping("/hello/{name}")
    public String helloString(@RequestParam String name) {
        return "hello " + name;
    }
}
