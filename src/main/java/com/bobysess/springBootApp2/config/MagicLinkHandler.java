package com.bobysess.springBootApp2.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Component
public class MagicLinkHandler implements OneTimeTokenGenerationSuccessHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(MagicLinkHandler.class);
    
    private final JavaMailSender mailSender;

    private final OneTimeTokenGenerationSuccessHandler redirectHandler = new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken token)
            throws IOException, ServletException {
        String magicLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() 
                         + "/login/ott?token=" + token.getTokenValue();
        
        // Get email from request parameter (you might want to get this from the user database)
        String email = request.getParameter("username"); // Assuming username is email
        
        if (email != null && !email.isEmpty()) {
            sendMagicLinkEmail(email, magicLink);
        }
        
        this.redirectHandler.handle(request, response, token);
    }
    
    private void sendMagicLinkEmail(String email, String magicLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Spring Security One Time Token");
        message.setText("Use the following link to sign in into the application: " + magicLink);
        message.setFrom("noreply@yourapp.com"); // Set your from address
        
        try {
            this.mailSender.send(message);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", email, e.getMessage());
        }
    }
}

