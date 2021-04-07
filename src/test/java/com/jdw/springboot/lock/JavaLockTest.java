package com.jdw.springboot.lock;


import lombok.SneakyThrows;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author ListJiang
 * @class Java基础锁
 * @remark
 * @date 2021/3/27 12:14
 */

class Ticket {

    // 总数
    private AtomicInteger size = new AtomicInteger(2000);


    public Ticket() {
    }

    public Ticket(int size) {
        this.size.set(size);
    }

    // 减一
    public boolean sell() {
        if (size.get() > 0) {
            int i = size.decrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    // 获取剩余数量
    public int getSize() {
        return size.get();
    }
}

class People {

    // 获取的ticket数量
    private int ticketSize = 0;

    // 人名
    private String name;

    public People(String name) {
        this.name = name;
    }

    @SneakyThrows
    public void buy(Ticket ticket) {
        /**
         * 这个代码块就是购买操作，如果此处不做处理，电脑好的话是不会出现线程不安全问题。
         * 所以我们手动延长执行时间
         */
        {
            Thread.sleep(100);
            if (ticket.sell()) {
                ticketSize++;
            } else {
                System.out.println(name + "买票失败，目前有" + ticketSize + "张票");
            }
        }
    }

    public int getTicketSize() {
        return ticketSize;
    }

    public String getName() {
        return name;
    }
}

public class JavaLockTest {

    /**
     * 打印票数
     *
     * @param ticket 卖票方
     * @param people 买票方
     */
    public void print(Ticket ticket, People... people) {
        System.out.println("目前情况是：");
        System.out.println("总共剩余：" + ticket.getSize() + "张票");
        for (People person : people) {
            System.out.println(person.getName() + "持有：" + person.getTicketSize() + "张票");
        }
    }

    @Test
    public void test1() throws InterruptedException {
        final Ticket ticket = new Ticket(1000);
        People A = new People("A");
        People B = new People("B");
        People C = new People("C");
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                A.buy(ticket);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                B.buy(ticket);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                C.buy(ticket);
            }
        }).start();

        Thread.sleep(10000);
        print(ticket, A, B);
    }


    private int testA = 1000;
    private int testB = 0;

    @Test
    public void test2() {
        Long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Runnable runnable = () -> {
            {
                // 总金额
                testA--;

                // 取出的金额
                testB++;
            }
        };


        Flux.range(1, 1000)// 从1开始构建1000个元素
                .toStream() // 转成 单线程流
                .collect(Collectors.toList()) // 以list方式进行收集
                .parallelStream()   // 转为并行流，
                .forEach(t -> {
                    runnable.run();
                    System.out.println(Thread.currentThread().getName());
                });
        System.out.println("testA：" + testA);
        System.out.println("testB：" + testB);
        System.out.println("1000个任务耗时：" + (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "ms");
    }


}

