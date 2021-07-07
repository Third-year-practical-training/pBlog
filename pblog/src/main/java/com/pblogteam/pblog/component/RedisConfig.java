package com.pblogteam.pblog.component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
@EnableCaching
@Configuration
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

//    /**
//     * 配置缓存
//     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        // 定义缓存 key 前缀，默认为 ::
//        final String keyPrefix = ":";
//        // 缓存对象配置
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                // 设置缓存默认超时时间：600 秒
//                .entryTtl(Duration.ofSeconds(1800))
//                // 设置 key 序列化器
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                // 设置 value 序列化器
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                // 如果该值为 null，则不缓存
//                .computePrefixWith(cacheName -> cacheName + keyPrefix)
//                .disableCachingNullValues();
//        // 根据 redis 缓存配置生成 redis 缓存管理器
//        return RedisCacheManager.builder(factory)
//                .cacheDefaults(config)
//                .transactionAware()
//                .build();
//    }

    @Bean
    public CacheManager oneDayCacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 1天缓存过期
                .entryTtl(Duration.ofSeconds(1800))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .computePrefixWith(name -> name + ":")
                .disableCachingNullValues();
        return new CustomizedRedisCacheManager(factory, config);
    }
}
