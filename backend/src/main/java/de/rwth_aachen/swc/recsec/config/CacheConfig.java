package de.rwth_aachen.swc.recsec.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Configuration class for enabling and setting up caching using Redis.
 *
 * This configuration uses Spring's caching abstraction and configures
 * a Redis-backed cache with custom serialization for keys and values.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configures the RedisCacheManager bean.
     *
     * @param connectionFactory the RedisConnectionFactory to establish connections to the Redis server
     * @return a configured instance of RedisCacheManager
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer(
                                new ObjectMapper().activateDefaultTyping(
                                        LaissezFaireSubTypeValidator.instance,
                                        ObjectMapper.DefaultTyping.NON_FINAL))));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .build();
    }
}