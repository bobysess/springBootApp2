package com.bobysess.springBootApp2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootApp2Application {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp2Application.class, args);
	}


	@Bean
	ApplicationRunner websocketClient () {
		return args -> {
			 HttpClient client = HttpClient.newHttpClient(); 
			 client.newWebSocketBuilder().buildAsync(URI.create("ws://localhost:8080/ws"), new WebSocket.Listener() {
				@Override
				public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
					System.out.println("Received: " + data);
					return WebSocket.Listener.super.onText(webSocket, data, last);
				}
			 })
			 .thenAccept(webSocket -> {
				webSocket.sendText("Hello from Java client", true);
			 });
		};
	}
}
