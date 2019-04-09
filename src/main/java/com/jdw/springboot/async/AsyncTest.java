package com.jdw.springboot.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ListJiang
 * @class 异步测试类
 * @remark
 * @date 2020/6/910:21
 */
@Component
public class AsyncTest {
    /**
     * 标明这个方法是异步操作
     * @param string
     */
    @Async
    public void test1(String string){
        System.out.println("这是 test1 异步方法"+string+(new Date()).getTime());
    }
    @Async
    public void test2(){
        System.out.println("这是 test2 异步方法"+(new Date()).getTime());
        for (int i = 0; i < 10; i++) {
            System.out.println("这是test2,现场id是："+Thread.currentThread().getId());
        }
    }

}
