package com.bobysess.springBootApp2.session;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisIndexedSessionRepository.RedisSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SessionController {
    private final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @GetMapping("/session")
    public String sessioSetAttr(HttpSession session) {
        return "Session : name Attr: " + session.getAttribute("person");
    }

    @GetMapping("/session/{name}")
    public String sessionGetAttr(HttpSession session, @PathVariable String name) {
        session.setAttribute("name", name);
        session.setAttribute("person", new User(name, "Tseumeugne", 18, Instant.now(), "t.arsel@yahoo.fr"));
        return "Session :" + session.getId();
    }

    @GetMapping("/session/invalidate")
    public void sessionInvalidate(HttpSession session) {
        session.invalidate();
    }

    public record User(String firstname, String lastname, int old, Instant birthDate, String email)
            implements Serializable {
    }
}
