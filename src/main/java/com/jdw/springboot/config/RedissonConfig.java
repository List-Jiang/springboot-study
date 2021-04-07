package com.jdw.springboot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ListJiang
 * @class
 * @remark
 * @date 2021/3/7 14:31
 */
@Configuration
public class RedissonConfig {

    @Autowired
    RedisProperties redisProperties;

    /*@Bean
    RedissonClient redissonSentinel() throws IOException {
        InputStream in = new FileInputStream("D:\\ideaProject\\idea2019\\springboot-study\\target\\classes\\redisson\\redisson-dev.yml");
        Config config1 = Config.fromYAML(in);
        return Redisson.create(config1);
    }*/

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        List<String> nodes = redisProperties.getSentinel().getNodes();
        List<String> newNodes = redisProperties.getSentinel().getNodes().stream()
                .map(redisProperties.isSsl() ? "rediss://"::concat : "redis://"::concat)
                .collect(Collectors.toList());
        String[] strings = newNodes.toArray(new String[3]);
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
                .addSentinelAddress(newNodes.toArray(new String[3]))
                .setMasterName(redisProperties.getSentinel().getMaster())
                .setPassword(redisProperties.getPassword())
//                .setUsername(redisProperties.getUsername())
//                .setSentinelPassword(redisProperties.getPassword())
                .setCheckSentinelsList(false)
                .setReadMode(ReadMode.MASTER);

        return Redisson.create(config);
    }
}
