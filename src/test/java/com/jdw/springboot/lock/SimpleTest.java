package com.jdw.springboot.lock;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 线程安全问题简单测试
 * 简单业务逻辑测试，一个数值减，一个数值加。确保两数之和不会因为多线程操作而出错即为线程安全
 * @author ListJiang
 * @since 2021/3/29 17:07
 */
public class SimpleTest {

    private int A = 100;
    private int B = 0;

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
            A--;
            sleep_100();
            B++;
            sleep_100();
            System.out.println(Thread.currentThread().getName());
        }

    };

    @Test
    public void Test1() {
        Flux.range(1, 100).toStream().collect(Collectors.toList())
                .parallelStream() // 转为并行流，会使用基于jdk的默认基础线程池 ForkJoinPool.commonPool，默认该线程池线程数为当前cpu核数
                .forEach(e -> runnable1.run());
        System.out.println("A===>" + A);
        System.out.println("B===>" + B);
    }

    // 业务代码二：给业务处理代码块加上synchronized关键字，锁基于 SimpleTest.class 构建
    Runnable runnable2 = () -> {

        synchronized (SimpleTest.class) {
            sleep_100();
            A--;
            sleep_100();
            B++;
            sleep_100();
            System.out.println(Thread.currentThread().getName());
        }

    };

    @Test
    public void Test2() {
        Flux.range(1, 100).toStream().collect(Collectors.toList())
                .parallelStream() // 转为并行流，会使用基于jdk的默认基础线程池 ForkJoinPool.commonPool，默认该线程池线程数为当前cpu核数
                .forEach(e -> runnable2.run());
        System.out.println("A===>" + A);
        System.out.println("B===>" + B);
    }

    Lock lock = new ReentrantLock();
    // 业务代码三： 简单使用重入锁
    Runnable runnable3 = () -> {
        try {
            lock.lock();
            sleep_100();
            A--;
            sleep_100();
            B++;
            sleep_100();
            System.out.println(Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    };

    @Test
    public void Test3() {
        Flux.range(1, 100).toStream().collect(Collectors.toList())
                .parallelStream() // 转为并行流，会使用基于jdk的默认基础线程池 ForkJoinPool.commonPool，默认该线程池线程数为当前cpu核数
                .forEach(e -> runnable3.run());
        System.out.println("A===>" + A);
        System.out.println("B===>" + B);
    }


}