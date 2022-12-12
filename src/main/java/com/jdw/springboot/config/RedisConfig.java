package com.jdw.springboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;

/**
 * 开启缓存支持
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfig implements CachingConfigurer {

    private final RedisProperties redisProperties;


    @Bean
    @ConditionalOnProperty(prefix = "spring.redis", name = "sentinel.nodes")
    public LettuceConnectionFactory sentinelLettuceConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration()
                .master(redisProperties.getSentinel().getMaster());
        redisProperties.getSentinel().getNodes().forEach(
                node -> redisSentinelConfiguration.sentinel(node.split(":")[0], Integer.valueOf(node.split(":")[1])));
        if (StringUtils.isNoneEmpty(redisProperties.getPassword()))
            redisSentinelConfiguration.setPassword(redisProperties.getPassword());
        if (StringUtils.isNoneEmpty(redisProperties.getUsername()))
            redisSentinelConfiguration.setUsername(redisProperties.getUsername());
        redisSentinelConfiguration.setSentinelPassword(redisProperties.getSentinel().getPassword());
        return new LettuceConnectionFactory(redisSentinelConfiguration, LettuceClientConfiguration.builder()
                .readFrom(REPLICA_PREFERRED).build());
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.redis", name = "host")
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
        redisConfiguration.setHostName(redisProperties.getHost());
        redisConfiguration.setUsername(redisProperties.getUsername());
        redisConfiguration.setPassword(redisProperties.getPassword());
        return new LettuceConnectionFactory(redisConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        log.info(" --- redis config init --- ");
        // 设置序列化
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
        redisTemplate.afterPropertiesSet();
        redisTemplate.opsForSet().add("test", 123);
        System.out.println(redisTemplate.opsForSet().pop("test"));
        return redisTemplate;
    }


}
