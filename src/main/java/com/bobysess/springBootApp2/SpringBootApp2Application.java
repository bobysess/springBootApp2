package com.bobysess.springBootApp2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties.Restclient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class SpringBootApp2Application {
	
	private final Logger logger = LoggerFactory.getLogger(SpringBootApp2Application.class);

	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp2Application.class, args);
	}
	
	@RestController
	class HelloRestController {
		private final ObservationRegistry observationRegistry;

		public HelloRestController(ObservationRegistry observationRegistry) {
			this.observationRegistry = observationRegistry;
		}

		
		@Observed(name = "app.hello")
		@GetMapping("/hello/{name}")
		public String hello(@PathVariable String name) {
			logger.info("Inside observation");
			return "Hello " + name;
		}

		@Timed(value = "app.hello3")
		public void hello3 (String name) {
			logger.info("logging name {}", name);			
		}
	}

	@Bean
	RestClient httpclient() {
		return RestClient.builder().baseUrl("www.google.com").build();
	}

	@Bean
	ApplicationRunner runApi(RestClient restclient) {
		return args -> {
			logger.info("Starting Api Call");
			restclient.get();
			logger.info("End Api Call");
		};
	}

}
