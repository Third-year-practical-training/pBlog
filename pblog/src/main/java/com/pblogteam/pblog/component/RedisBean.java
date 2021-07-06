package com.pblogteam.pblog.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisBean {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static StringRedisTemplate redis;

    @PostConstruct
    public void getRedisTemplate() {
        redis = stringRedisTemplate;
    }
}
