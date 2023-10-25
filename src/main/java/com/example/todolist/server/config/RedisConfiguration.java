package com.example.todolist.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate=new RedisTemplate<>();
        //设置redis连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置序列号
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
