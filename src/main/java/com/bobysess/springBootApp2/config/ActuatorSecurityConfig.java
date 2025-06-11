package com.bobysess.springBootApp2.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class ActuatorSecurityConfig {

    // This security filter chain is applied to actuator endpoints
    @Bean
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
            .anyRequest().permitAll(); 
        }).httpBasic(config -> {
            
        });
        
        // http
        //     .securityMatcher(EndpointRequest.toAnyEndpoint())
        //     .authorizeHttpRequests(authorize -> authorize
        //         // We allow the health and info endpoints to be accessed without authentication
        //         .requestMatchers(EndpointRequest.to("health", "info")).permitAll()
        //         // All other actuator endpoints require ACTUATOR_ADMIN role
        //         .anyRequest().hasRole("ACTUATOR_ADMIN")
        //     )
        //     .httpBasic();
        
        return http.build();
    }
    
    // This security filter chain is applied to the rest of the application
    // @Bean
    // public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(authorize -> authorize
    //             .anyRequest().permitAll()
    //         );
        
    //     return http.build();
    // }
}
