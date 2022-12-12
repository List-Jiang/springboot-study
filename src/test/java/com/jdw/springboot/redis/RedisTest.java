package com.jdw.springboot.redis;

import com.jdw.SpringbootApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 蒋德文
 * @class redis测试
 * @since 2021/3/7 18:16
 */
@SpringBootTest(classes = SpringbootApplication.class)
@Slf4j
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void Test1() {
        String key1 = "key1";
        redisTemplate.opsForValue().set(key1, "testValue1");
        System.out.println("key1:" + redisTemplate.opsForValue().get(key1));
        redisTemplate.opsForValue().set(key1, "testValue1第三方");
        System.out.println("key1:" + redisTemplate.opsForValue().get(key1));
        redisTemplate.opsForValue().set(key1, "testValue2");
        System.out.println("key1:" + redisTemplate.opsForValue().get(key1));
        redisTemplate.opsForValue().set(key1, 1234567);
        System.out.println("key1:" + redisTemplate.opsForValue().get(key1));
    }

}