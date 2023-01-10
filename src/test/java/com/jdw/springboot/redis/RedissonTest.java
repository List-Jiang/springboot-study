package com.jdw.springboot.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;


@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedissonTest {
    private RedissonClient redissonClient;

    @BeforeAll
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
    }

    private final String lockName = "lockName1";

    private Integer size = 1000;

    private int count = 0;

    @Test
    public void Test1() {

        long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Runnable runnableLock = () -> {
            RLock lock = null;
            try {
                lock = redissonClient.getLock(lockName);
                lock.lock(30, TimeUnit.SECONDS);
                size--;
                count++;
                lock.unlock();
            } catch (Exception exception) {
                if (lock != null)
                    try {
                        lock.unlock();
                    } catch (Exception e) {
                        System.out.println("释放锁报错");
                    }
            }
        };
        Runnable runnable = () -> {

            {

                size--;

                count++;

            }

        };
        Flux.range(1, 1000)
                .toStream()
                .parallel()
                .forEach(t -> runnableLock.run());
        System.out.println("size：" + size);
        System.out.println("count：" + count);
        System.out.println("1000个任务耗时：" + (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "ms");
    }

}