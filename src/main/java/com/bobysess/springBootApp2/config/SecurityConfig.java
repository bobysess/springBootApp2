package com.bobysess.springBootApp2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.PasswordComparisonAuthenticator;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                //.requestMatchers("/", "/home", "/public/**").permitAll()
                //.requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
            // .csrf(csrf -> csrf
            //     .ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 console
            // )
            // .headers(headers -> headers
            //     .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow frames from same origin for H2 console
            // );

        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.builder()
    //         .username("user")
    //         .password(passwordEncoder().encode("password"))
    //         .roles("USER")
    //         .build();

    //     UserDetails admin = User.builder()
    //         .username("admin1")
    //         .password(passwordEncoder().encode("admin1"))
    //         .roles("USER", "ADMIN")
    //         .build();

    //     return new InMemoryUserDetailsManager(user, admin);
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:1389");
        contextSource.setBase("dc=example,dc=org");
        return contextSource;
    }

    @Bean
    public AuthenticationManager ldapAuthenticationManager() {
        PasswordComparisonAuthenticator authenticator = 
            new PasswordComparisonAuthenticator(ldapContextSource());
        authenticator.setUserDnPatterns(new String[] {"cn={0},ou=users"});
        authenticator.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        
        LdapAuthenticationProvider provider = 
            new LdapAuthenticationProvider(authenticator, ldapAuthoritiesPopulator());
        
        return provider::authenticate;
    }

    @Bean
    public LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
        DefaultLdapAuthoritiesPopulator authoritiesPopulator = 
            new DefaultLdapAuthoritiesPopulator(ldapContextSource(), "ou=groups");
        authoritiesPopulator.setGroupRoleAttribute("cn");
        authoritiesPopulator.setGroupSearchFilter("(member={0})");
        authoritiesPopulator.setDefaultRole("USER");
        return authoritiesPopulator;
    }
}
