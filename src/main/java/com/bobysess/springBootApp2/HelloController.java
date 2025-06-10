package com.bobysess.springBootApp2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelloController {
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello world " + name;
    }
}
