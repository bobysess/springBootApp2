package com.bobysess.springBootApp2.Api;

import org.springframework.web.bind.annotation.RestController;

import com.bobysess.springBootApp2.service.HelloService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

@RestController
public class HelloController {

    private final MeterRegistry meterRegistry;
    private final HelloService helloService;
    private long gauge = 0l;

    public HelloController(MeterRegistry meterRegistry, HelloService helloService) {
        this.meterRegistry = meterRegistry;
        this.helloService = helloService; 
    }

    @Timed(value = "hello.request.time")
    @Counted(value = "hello.request.count")
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return helloService.hello3(name);
    }

    
    @GetMapping("/count")
    public long counter() {
        return gauge;
    }

    @GetMapping("/wait")
    public String waitOperation() {

        meterRegistry.timer("customer.timer").record(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
        return "End Waiting";
    }


    @Bean public ApplicationRunner applicationRunner(MeterRegistry meterRegistry) {
        return args -> {
            meterRegistry.counter("customer.count", "customer", "count", "app", "spring boot").increment();
            meterRegistry.counter("customer.count", "customer", "count", "app", "spring boot").increment();
            meterRegistry.gauge("customer.gauge", 3);
        };
    }

}
