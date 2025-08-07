package com.loyaltyProgramPOC.loyaltyProgram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RequiredArgsConstructor
@Service
@Slf4j
public class JedisService {

    @Autowired
    private final JedisPool jedisPool;

//    public void setValue(String key, String value) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set(key, value);
//        }
//    }

    public void setValue(String key, Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(value);
            jedis.set(key, json);
        } catch (Exception e) {
            log.error("Error setting value in Redis", e);
            throw new RuntimeException(e);
        }
    }

//    public void setValueWithExpiry(String key, String value, long seconds) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.set(key, value);
//            jedis.expire(key, seconds);
//        }
//    }

    public void setValueWithExpiry(String key, Object value, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(value);
            jedis.setex(key, seconds, json);
        } catch (Exception e) {
            log.error("Error setting value with expiry in Redis", e);
            throw new RuntimeException(e);
        }
    }

//    public String getValue(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            return jedis.get(key);
//        }
//    }

    public <T> T getValue(String key, Class<T> clazz) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get(key);
            if (json == null) return null;
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Error getting value from Redis", e);
            throw new RuntimeException(e);
        }
    }

    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }
}