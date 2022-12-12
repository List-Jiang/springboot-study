package com.jdw.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ListJiang
 * @class 线程池配置类
 * @remark
 * @date 2020/7/2420:50
 */
@Configuration
public class ThreadPoolConfig {
    /**
     * 该线程池SpringBoot默认自动装配了,此处手写是为了更好的理解
     * 主要是为了，重写线程池拒绝策略
     *
     * @return
     */
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        // 线程 池 任务 执行者
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);//核心线程数
        taskExecutor.setMaxPoolSize(50);//最大线程数
        taskExecutor.setQueueCapacity(200);//线程等待队列容量
        taskExecutor.setKeepAliveSeconds(60);//线程存活时间
        taskExecutor.setThreadNamePrefix("taskExecutor--");//线程名前缀
        /**
         * 线程池拒绝策略
         * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());//线程池拒绝策略
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);//线程池必须等待所有线程执行完毕才能销毁
        taskExecutor.setAwaitTerminationSeconds(60);//线程池销毁等待时间设置为60s，在线程池关闭之前，等待指定时间过后再销毁
        taskExecutor.initialize();//线程池初始化

        return taskExecutor;
    }

    /**
     * 定时任务
     *
     * @return
     */
    @Bean("taskScheduler")
    public Executor taskScheduler() {
        // 线程池任务调度器
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        /**
         * 线程池拒绝策略
         * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
         */
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());//线程池拒绝策略
        taskScheduler.setPoolSize(20);//池大小
        taskScheduler.setThreadNamePrefix("taskSchedule--");//线程名字前缀
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);//线程池必须等待所有线程执行完毕才能销毁
        taskScheduler.setAwaitTerminationSeconds(60);//线程池最长等待时间设置为60s
        return taskScheduler;
    }
}
