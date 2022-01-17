package com.jdw.springboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import static io.lettuce.core.ReadFrom.*;

/**
 * 开启缓存支持
 *
 * @Return:
 */
@Slf4j
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    RedisProperties redisProperties;


    /**
     * RedisTemplate配置
     *
     * @param lettuceConnectionFactory
     * @return
     */
    /**
     * Lettuce
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.redis", name = "sentinel.nodes")
    public LettuceConnectionFactory sentinelLettuceConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration()
                .master(redisProperties.getSentinel().getMaster());
        redisProperties.getSentinel().getNodes().forEach(
                node -> redisSentinelConfiguration.sentinel(node.split(":")[0], Integer.valueOf(node.split(":")[1])));
            redisSentinelConfiguration.setPassword(redisProperties.getPassword());
//            redisSentinelConfiguration.setUsername(redisProperties.getUsername());
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
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
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