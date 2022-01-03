package com.jdw.springboot.task;

import com.jdw.springboot.async.AsyncTest;
import com.jdw.springboot.config.CustomVariableConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Autowired
    private AsyncTest asyncTest;
    @Autowired
    private CustomVariableConfig variableConfig;
    private static int t = 0;
//    标明这个方法是个定时任务,每两秒创建一个等待上一个完成后执行。
//    上一个执行完毕，新的立即执行。
//    上一个未执行完毕，新的不会执行,如果上一个的执行时间超过了两秒，则上一个执行完毕后，新的立即执行。
//    @Scheduled(fixedRate = 2000L)

//    标明这个方法是个定时任务,上一个定时任务完成后2秒新建定时任务开始执行。
//    @Scheduled(fixedDelay = 2000L)

//    标明这个方法是个定时任务,每四秒钟判断上一个是否已执行,上一个执行完毕则新建定时任务开始执行。未执行完毕则等待4秒后继续判断
    @Scheduled(cron = "0/4 * * * * ?")
    private void task1() throws Exception{
        t++;
        System.out.println("task"+t+"：定时任务开始"+(new Date()).getTime()+ variableConfig.getCustomValue1());
        Thread.sleep(2000);
        System.out.println("调用异步前");
        for (int i = 0; i < 10; i++) {
            asyncTest.test1(""+i);
        }
        System.out.println("调用异步后");
    }
}
