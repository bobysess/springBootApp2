package com.bobysess.springBootApp2.config;

import com.bobysess.springBootApp2.hello.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.config.ldap.LdapPasswordComparisonAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> {
                csrf.ignoringRequestMatchers("/h2-console/**");
            })
            .sessionManagement(s -> {
                //s.maximumSessions(1);
                s.disable();
            })
            .headers(headers -> {
                //headers.sameOrigin();
            })
            .authorizeHttpRequests(authz -> authz
                 .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/hello/**").permitAll()
                    .requestMatchers("/failure.html/**").permitAll()
                    .anyRequest().authenticated())
            .formLogin(Customizer.withDefaults())
            .anonymous(anonymous -> {
                anonymous.authorities("USER");
                anonymous.principal("user");
            })
            .oneTimeTokenLogin(ott -> {
                
            });
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService  userDetailsService(DataSource dataSource) {
         return new InMemoryUserDetailsManager(
                 User.withUsername("admin").password("{noop}admin").authorities("ADMIN").build(),
                 User.withUsername("user").password("{noop}user").authorities("USER").build()
         );
    }
}
