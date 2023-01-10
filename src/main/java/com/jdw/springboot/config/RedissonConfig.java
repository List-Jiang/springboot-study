package com.jdw.springboot.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author 蒋德文
 * @class
 * @since 2021/3/7 14:31
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfig {

    private final RedisProperties redisProperties;

    @Bean("redissonClient")
    @ConditionalOnProperty(prefix = "spring.redis", name = "sentinel.nodes")
    RedissonClient redissonSentinelClient() {
        Config config = new Config();
        List<String> newNodes = redisProperties
                .getSentinel()
                .getNodes().stream()
                .map(redisProperties.isSsl() ? "rediss://"::concat : "redis://"::concat)
                .toList();
        config.useSentinelServers()
                .addSentinelAddress(newNodes.toArray(new String[3]))
                .setMasterName(redisProperties.getSentinel().getMaster())
                .setPassword(redisProperties.getPassword())
                .setUsername(redisProperties.getUsername())
                .setCheckSentinelsList(false)
                .setReadMode(ReadMode.MASTER);

        return Redisson.create(config);
    }

    @Bean("redissonClient")
    @ConditionalOnProperty(prefix = "spring.redis", name = "url")
    RedissonClient SingleServerConfig() {
        Config config = new Config();
        config.useSingleServer()
                .setUsername(redisProperties.getUsername())
                .setPassword(redisProperties.getPassword())
                .setAddress(redisProperties.getUrl());
        return Redisson.create(config);
    }

}
