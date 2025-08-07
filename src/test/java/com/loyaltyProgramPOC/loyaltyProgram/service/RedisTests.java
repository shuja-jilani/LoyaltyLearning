package com.loyaltyProgramPOC.loyaltyProgram.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void testSendName(){
        redisTemplate.opsForValue().set("name2","ahuja");
        Object name = redisTemplate.opsForValue().get("salary");
        System.out.println(name);
    }
}
