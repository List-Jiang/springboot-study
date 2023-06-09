package com.jdw.springboot.task;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池测试类
 *
 * @author 蒋德文
 * @since 2020/7/2421:27
 */
public class TaskPoolTest {

    private static final RestTemplate restTemplate = new RestTemplateBuilder().build();

    /**
     * 线程池默认策略
     * 线程池拒绝策略 AbortPolicy 测试
     * 丢弃任务，抛出拒绝执行异常 RejectedExecutionException
     * 测试结果，创建10个任务。全部试图调用线程池执行。
     * 除了直接执行的任务与进入等待队列的任务，其余的任务全部抛弃，并抛出 RejectedExecutionException
     */
    @Test
    public void AbortPolicyDemo() {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为2，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));
        // 设置线程池的拒绝策略为"中止",会抛出拒绝执行异常
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 新建10个任务，并将他们添加到线程中
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(() -> System.out.println("线程" + finalI));
        }
        pool.shutdown();
    }

    /**
     * 线程池拒绝策略 DiscardPolicyDemo 测试
     * 丢弃任务，但是不抛出异常。
     * 测试结果，创建10个任务。全部试图调用线程池执行。
     * 除了直接执行的任务与进入等待队列的任务，其余的任务全部抛弃。
     */
    @Test
    public void DiscardPolicyDemo() {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为2，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));
        // 设置线程池的拒绝策略为"丢弃"。
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 新建10个任务，并将他们添加到线程中
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(() -> System.out.println("线程" + finalI));
        }
        pool.shutdown();
    }

    /**
     * 线程池拒绝策略 CallerRunsPolicy 测试
     * 由调用线程执行线程持拒绝的任务
     * 测试结果，创建10个任务。全部试图调用线程池执行。
     * 第1,2个任务进入线程池，异步执行。第3个任务进入线程等待队列
     * 第 n 个任务，如果被拒绝则由调用线程（该案例中为 main）直接执行任务。
     */
    @Test
    public void CallerRunsPolicyDemo() {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为2，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1));
        // 设置线程池的拒绝策略为"谁调用谁执行"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 新建10个任务，并将他们添加到线程中
        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000);
            int finalI = i;
            pool.execute(() -> {
                System.out.print("第" + (finalI + 1) + "个任务：");
                System.out.println("线程" + Thread.currentThread().getName());
            });
        }
        pool.shutdown();
    }

    /**
     * 线程池拒绝策略 DiscardOldestPolicy 测试
     * 抛弃最老队列任务策略
     * 测试结果，创建10个任务。全部试图调用线程池执行。
     * 前两个任务正常执行，后面的新任务进来都采用抛弃掉等待队列里面最老任务的策略
     */
    @Test
    public void DiscardOldestPolicyDemo() {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为2，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        // 设置线程池的拒绝策略为"丢弃队列最老等待任务"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        // 新建10个任务，并将他们添加到线程中
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(() -> {
                System.out.print("第" + (finalI + 1) + "个任务：");
                System.out.println("线程" + Thread.currentThread().getName());
            });
        }
        pool.shutdown();
    }


    @Test
    public void DiscardOldestPolicyDemo1() {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为2，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 50, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));
        // 设置线程池的拒绝策略为"丢弃队列最老等待任务"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        String url = "http://localhost:8082/api1/forward/gcjs_lhch_wt/1c4e88acd48d0c03783a1441075cb160?i=1";
        String url2 = "http://localhost:8082/getChukuNumber?type=ewfrqw";
        String url3 = "http://localhost:8082/getChukuNumber2?type=ewrew";
        //                "    \"i\":\"1\"\n" +
        JSONObject jsonObject = JSON.parseObject("""
                {
                    "key":"value",
                }""");
        // 新建10个任务，并将他们添加到线程中
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(() -> {
                jsonObject.put("i", finalI + 1);
                String body = restTemplate.postForObject(url2, null, String.class);
                System.out.println("第" + (finalI + 1) + "个任务：" + body);
            });
        }
        pool.shutdown();
    }

    @Test
    public void test() {
        String str = "[/apifile/mongo/view/{_id} || /apifile/mongo/view/{_id}]";
        String substring = str.substring(1, str.length() - 1);
        String string = substring.replaceAll("\\|\\|", ",").replaceAll(" ", "");
        System.out.println(string);
        String url2 = "http://localhost:8082/readThreadPool?type=ewfrqw";
        //                "    \"i\":\"1\"\n" +
        JSONObject jsonObject = JSON.parseObject("""
                {
                    "key":"value",
                }""");
        Map<String, String> t = new HashMap<>();
        t.put("dfsdfs", "放大方式");
        System.out.println(t);
        for (int i = 0; i < 10; i++) {
            String body = restTemplate.postForObject(url2 + i, jsonObject, String.class);
            System.out.println(body);
        }

    }

}