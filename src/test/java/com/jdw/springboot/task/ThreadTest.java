package com.jdw.springboot.task;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

/**
 * @author ListJiang
 * @class
 * @remark
 * @date 2021/4/7 10:58
 */
@Slf4j
//@SpringBootTest(classes = SpringbootApplication.class)
public class ThreadTest {

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    public static Callable<String> callable(long millis, String message) {
        return () -> {
            Thread.sleep(millis);
            System.out.println(message);
            return Thread.currentThread().getName() + "：" + message;
        };
    }

    public static Supplier<String> supplier(long millis, String message) {
        return () -> {
            try {
                Thread.sleep(millis);
                System.out.println(Thread.currentThread().getName() + "：" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Thread.currentThread().getName() + "：" + message;
        };
    }

    @Test
    public void test() throws InterruptedException {
        int size = 200;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            final int t = i;
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 2000; j++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("key", Math.random());
                }
                System.out.println(t + "==>" + Instant.now().toEpochMilli() + "==>" + Thread.currentThread().getName());
            });
            threads[i] = thread;
        }
        for (Thread thread : threads) {
            thread.start();
        }
        Thread.sleep(30000);
    }

    @Test
    public void CallableTest() throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            System.out.println(Instant.now().toEpochMilli() + "==>" + Thread.currentThread().getName());
            return "任务执行完毕";
        };
        String s = taskExecutor.submit(callable).get();
        System.out.println(s);
    }

    @SneakyThrows
    @Test
    public void futureTaskTest() {

        long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

        JSONObject result = new JSONObject();
        Callable callable1 = ThreadTest.callable(300, "任务1耗时300毫秒");
        Callable callable2 = ThreadTest.callable(200, "任务2耗时200毫秒");

        FutureTask<String> task1 = new FutureTask<>(callable1);
        FutureTask<String> task2 = new FutureTask<>(callable2);
        // 此处注意，使用 new Thread 构建新线程，并且使用 start 方法启动线程才是新建线程执行。
        // 使用 task1.run() 与 new Thread(task1).run() 皆是由调用线程执行，不是新建线程。
        new Thread(task1).start();
        new Thread(task2).start();

        log.info(task1.get());
        log.info(task2.get());
        log.info("任务总耗时" + (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "毫秒");
    }

    /**
     * 两个任务串行执行，第一个执行的结果用于第二个执行。全部执行完成，任务结束。返回结果
     * 串行
     * {}
     * {}
     */
    @Test
    public void completableFuture_thenComposeTest() {
        long start = System.currentTimeMillis();

        Supplier<String> supplier1 = ThreadTest.supplier(1000, "任务1耗时1000毫秒结束");


        Supplier<String> supplier2 = ThreadTest.supplier(2000, "任务2耗时2000毫秒结束");


        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(supplier1)

                .thenCompose(c -> CompletableFuture.supplyAsync(ThreadTest.supplier(2000, c + "任务2耗时2000毫秒")));
        System.out.println(future.join());

        log.info("任务总耗时" + (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "毫秒");

    }

    /**
     * 两个任务并行执行，全部执行完成，任务结束。返回合并结果
     * AND 聚合
     */
    @Test
    public void completableFuture_thenCombineTest() {
        long start = System.currentTimeMillis();

        Supplier<String> supplier2 = ThreadTest.supplier(200, "任务2耗时200毫秒");
        Supplier<String> supplier1 = ThreadTest.supplier(100, "任务1耗时100毫秒");

        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(supplier1)
                .thenCombine(CompletableFuture.supplyAsync(supplier2), (a, b) -> a + b);
        System.out.println(future.join());

        log.info("任务总耗时" + (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "毫秒");

    }

    /**
     * 两个任务并行执行，谁先执行完返回谁。没执行完毕的依然会在后台继续执行
     * OR 聚合
     */
    @Test
    public void completableFuture_applyToEitherTest() {
        long start = Instant.now().toEpochMilli();

        Supplier<String> supplier1 = ThreadTest.supplier(100, "任务1耗时100毫秒");
        Supplier<String> supplier2 = ThreadTest.supplier(200, "任务2耗时200毫秒");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier1)
                .applyToEither(CompletableFuture.supplyAsync(supplier2), t -> t);

        System.out.println(future.join());
        log.info("任务总耗时" + (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "毫秒");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void completableFuture_Exption() {
        long start = Instant.now().toEpochMilli();

        Supplier<String> supplier1 = ThreadTest.supplier(100, "任务1耗时100毫秒");
        Supplier<String> supplier2 = ThreadTest.supplier(-1, "任务2耗时200毫秒");

        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(supplier1)
                .applyToEither(CompletableFuture.supplyAsync(supplier2), t -> t)
                // 异常处理
                .exceptionally(e -> {
                    System.out.println("发生异常：" + e.getMessage());
                    return "发生异常";
                });

        System.out.println("任务结果：" + future.join());
        System.out.println("任务总耗时：" + (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "毫秒");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** =======================多个任务同时执行===================*/
    /**
     * 全部执行完毕，无返回值
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void compleatbleFuture_AllOf() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> futureOne = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("futureOne InterruptedException");
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
            return "futureOneResult";
        });
        final CompletableFuture<String> futureTwo = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                System.out.println("futureTwo InterruptedException");
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
            return "futureTwoResult";
        });
        CompletableFuture future = CompletableFuture.allOf(futureOne, futureTwo);


        System.out.println(future.get());
    }

    /**
     * 谁先执行，返回谁，剩下的不执行
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void compleatbleFuture_AnyOf() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> futureOne = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("futureOne InterruptedException");
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
            return "futureOneResult";
        });
        final CompletableFuture<String> futureTwo = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                System.out.println("futureTwo InterruptedException");
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
            return "futureTwoResult";
        });
        CompletableFuture future = CompletableFuture.anyOf(futureOne, futureTwo);
        System.out.println(future.get());
    }
    /** =======================多个任务同时执行===================*/
}