package com.jdw.sys.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ListJiang
 * @class 多线程执行任务测试
 * @since 2021/4/7 10:07
 */
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @RequestMapping("/futureTask")
    public JSONObject futureTask() throws ExecutionException, InterruptedException {
        long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        JSONObject result = new JSONObject();
        Callable callable1 = () -> {
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + "任务1耗时100毫秒");
            return JSONObject.parseObject("{\"测试key\":\"任务1耗时100毫秒\"}");
        };

        Callable callable2 = () -> {
            Thread.sleep(200);
            System.out.println(Thread.currentThread().getName() + "任务2耗时200毫秒");
            return JSONObject.parseObject("{\"测试key\":\"任务2耗时200毫秒\"}");
        };

        FutureTask<JSONObject> task1 = new FutureTask<>(callable1);
        FutureTask<JSONObject> task2 = new FutureTask<>(callable2);

        task2.run();
        task1.run();
        JSONObject jsonObject2 = task2.get();
        JSONObject jsonObject1 = task1.get();
        result.put("jsonObject1", jsonObject1);
        result.put("jsonObject2", jsonObject2);
        result.put("任务耗时", (LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - start) + "毫秒");
        return result;
    }
}
