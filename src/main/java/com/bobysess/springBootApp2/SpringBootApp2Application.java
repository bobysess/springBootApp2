package com.bobysess.springBootApp2;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.devh.boot.grpc.client.inject.GrpcClient;


@SpringBootApplication
public class SpringBootApp2Application {

	@GrpcClient("local")
	public HelloServiceGrpc.HelloServiceBlockingStub stub;


	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp2Application.class, args);
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			System.out.println("----->>> " + stub.sayHello((HelloRequest.newBuilder().setName("Alien").build())).getMessage());
		};
	}
}
