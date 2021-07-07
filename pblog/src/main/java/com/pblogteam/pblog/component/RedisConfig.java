package com.pblogteam.pblog.component;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

public class RedisConfig extends CachingConfigurerSupport {
    /**
     * 配置 redisTemplate
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 实例化一个 redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 设置 key 序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置 value 序列化方式
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 设置 hash key 序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置 hash value 序列化方式
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    /**
     * 配置缓存
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 定义缓存 key 前缀，默认为 ::
        final String keyPrefix = ":";
        // 缓存对象配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存默认超时时间：600 秒
                .entryTtl(Duration.ofSeconds(600))
                // 设置 key 序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置 value 序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 如果该值为 null，则不缓存
                .computePrefixWith(cacheName -> cacheName + keyPrefix)
                .disableCachingNullValues();
        // 根据 redis 缓存配置生成 redis 缓存管理器
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }
}
