package com.jdw.springboot.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ListJiang
 * @class 定时任务测试类
 * @remark 定时任务测试类
 * @date 2020/6/815:01
 */
//交给 spring 管理
@Component
public class TimedTask {
    //标明这个方法是个定时任务
    @Scheduled(fixedRate = 2000)
    private void task1() throws Exception{
        System.out.println("2秒执行一遍定时任务");
    }
}
