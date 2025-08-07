package com.loyaltyProgramPOC.loyaltyProgram.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
public class JedisConfiguration {

    @Bean
    public JedisPool jedisPool(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port,
            @Value("${spring.data.redis.database}") String database) {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        return new JedisPool(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, null, Integer.parseInt(database));
    }
}