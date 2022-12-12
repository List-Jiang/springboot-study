package com.jdw.springboot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author 蒋德文
 * @class
 * @since 2021/3/7 14:31
 */
@Configuration
public class RedissonConfig {

    @Autowired
    RedisProperties redisProperties;

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
    @ConditionalOnProperty(prefix = "spring.redis", name = "host")
    RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisProperties.isSsl() ? "rediss://" : "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        if (StringUtils.hasLength(redisProperties.getPassword())) {
            config.useSingleServer().setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
