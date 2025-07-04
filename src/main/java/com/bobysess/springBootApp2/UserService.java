package com.bobysess.springBootApp2;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    @Cacheable(value = "users", key="#id")
    public User getUser(long id) {
        return users.get(id);
    }

    @Cacheable(value = "users", key = "'all'")
    public List<User> users() {
        return users.values().stream().toList();
    }

    @CacheEvict(value = "users", key = "#id")
    public void evict(long id) {

    }

    @CacheEvict(value = "users", allEntries = true)
    public void evictAll() {
    }

    record User(long id, String firstname, String lastname, int old) {
    }
    
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(10))
        .disableCachingNullValues()
        .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @PostConstruct
    void loadUsers() {
        users.put(1l,  new User(1l, "müller", "fabiolla", 1));
        users.put(2l,  new User(2l, "rebbeca", "ghyslain", 2));
        users.put(3l,  new User(3l, "arsel", "kevin", 3));
        users.put(4l,  new User(4l, "nelly", "vidy", 4));
    }
}
