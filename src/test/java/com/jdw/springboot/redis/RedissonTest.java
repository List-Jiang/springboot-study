package com.jdw.springboot.redis;

import com.jdw.springboot.SpringbootApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@Slf4j
public class RedissonTest {
    @Autowired
    private RedissonClient redissonClient;


    private final String lockName = "lockName1";

    private Integer size = 1000;

    private int count = 0;

    @Test
    public void Test1() {

        Long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
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
        Flux.range(1, 1000).toStream().collect(Collectors.toList()).parallelStream()
                .forEach(t -> {
                    runnable.run();
                });
        System.out.println("size：" + size);
        System.out.println("count：" + count);
        System.out.println("1000个任务耗时："+(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()-start)+"ms");
    }

}
