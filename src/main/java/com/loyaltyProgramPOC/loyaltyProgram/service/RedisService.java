package com.loyaltyProgramPOC.loyaltyProgram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass){
        try{
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Exception:",e);
            throw new RuntimeException(e);
        }
    }
    public void set(String key, Object o, Long ttl) {
        try {
            redisTemplate.opsForValue().set(key, o,ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception:", e);
        }
    }
}
