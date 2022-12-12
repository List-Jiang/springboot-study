package com.jdw.springboot.lock;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 蒋德文
 * @class JUC测试类
 * @since 2021/3/27 22:32
 */


public class JUCTest {

    // 票据资源
    static class Ticket {

        // 总数
        private int size = 2000;


        public Ticket() {
        }

        public Ticket(int size) {
            this.size = size;
        }

        // 减一
        public boolean sell() {
            return size-- > 0;
        }

        // 获取剩余数量
        public int getSize() {
            return size;
        }
    }

    // 票据消费
    class People {
        private int ticketSize = 0;

        private final String name;

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

    private void print(Ticket ticket, People... people) {
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

        ReentrantLock lock = new ReentrantLock(true);
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                lock.lock();//永远等待lock，没有lock就一直卡在这里
                {
                    A.buy(ticket);
                }
                lock.unlock();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                boolean b = false;
                try {
                    // 在100毫秒内尝试获取锁，如果100毫秒后还没有获取到锁，放回false
                    b = lock.tryLock(100, TimeUnit.MICROSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (b) {
                    try {
                        // b的业务代码
                        B.buy(ticket);
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("B获取锁失败！");
                }
            }
        }).start();
        Thread.sleep(6000);
        print(ticket, A, B);
    }


}