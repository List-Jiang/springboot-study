package com.jdw.springboot.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author 蒋德文
 * @class redis测试
 * @since 2021/3/7 18:16
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisTest {
    private RedissonClient redissonClient;

    @BeforeAll
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
    }

    @Test
    public void Test1() {
        RBucket<Object> rBucket = redissonClient.getBucket("test_key");
        String str1 = "testValue1";
        String str2 = "testValue1第三方";
        Integer str3 = 1234567;
        Double str4 = -123.23d;
        boolean str5 = true;
        rBucket.set(str1);
        Assertions.assertEquals(str1, rBucket.get());
        rBucket.set(str2);
        Assertions.assertEquals(str2, rBucket.get());
        rBucket.set(str3);
        Assertions.assertEquals(str3, rBucket.get());
        rBucket.set(str4);
        Assertions.assertEquals(str4, rBucket.get());
        rBucket.set(str5);
        Assertions.assertEquals(str5, rBucket.get());
    }

}