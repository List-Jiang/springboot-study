package com.jdw.springboot.lock;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 线程安全问题简单测试
 * 该方案其实是伪修复，只是确保了单个数据操作的原子性。如果中间出现问题，是无法保证线程安全的
 * @author ListJiang
 * @since 2021/3/29 17:07
 */
public class AtomicTest {

    private final AtomicInteger A = new AtomicInteger(100);
    private final AtomicInteger B = new AtomicInteger(0);

    private void sleep_100() {
        // 自定义当前线程休眠100毫秒，模拟业务数据处理带来的时间消耗
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 业务代码一：未进行任何处理
    Runnable runnable1 = () -> {

        {
            sleep_100();
            A.decrementAndGet();
            sleep_100();
            B.incrementAndGet();
            sleep_100();
            System.out.println(Thread.currentThread().getName());
        }

    };

    @Test
    public void Test1() {
        Flux.range(1, 100).toStream().collect(Collectors.toList())
                .parallelStream()
                .forEach(e -> runnable1.run());
        System.out.println("A===>" + A);
        System.out.println("B===>" + B);
    }


}