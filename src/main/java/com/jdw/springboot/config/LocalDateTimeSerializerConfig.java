package com.jdw.springboot.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ListJiang
 * @class LocalDateTime序列化配置
 * @remark 用于解决json转换时的格式问题
 * @date 2022/01/03
 */
@Configuration
public class LocalDateTimeSerializerConfig {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * String 转 LocalDate 转换器
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        // 此处 new Converter<String, LocalDate>() 不可简写
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (source.trim().length() == 0)
                    return null;
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(DATE_PATTERN));
            }
        };
    }

    /**
     * String 转 LocalDateTime 转换器
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        // 此处 new Converter<String, LocalDate>() 不可简写
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (source.trim().length() == 0)
                    return null;
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
            }
        };
    }

    /**
     * 统一配置 LocalDate、LocalDateTime、LocalTime 与 String 之间的互相转换
     * <p>
     * 最终效果:
     * {
     * "localDate": "2022-01-03",
     * "localDateTime": "2022-01-03 18:36:53",
     * "localTime": "18:36:53",
     * "date": "2022-01-03 18:36:53",
     * "calendar": "2022-01-03 18:36:53"
     * }
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_PATTERN);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_PATTERN)));
            builder.modules(module);
        };
    }

    /**
     * @author ListJiang
     * @class 线程池配置类
     * @remark
     * @date 2020/7/2420:50
     */
    @Configuration
    public static class ThreadPoolConfig {
        /**
         * 该线程池SpringBoot默认自动装配了,此处手写是为了更好的理解
         * 主要是为了，重写线程池拒绝策略
         *
         * @return
         */
        @Bean(name = "taskExecutor")
        public Executor taskExecutor() {
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
}
