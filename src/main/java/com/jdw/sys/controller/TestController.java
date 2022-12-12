package com.jdw.sys.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jdw.sys.entity.Demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 蒋德文
 * @class 请求相关测试类
 * @since 2020/8/2510:15
 */
@RestController
public class TestController {
    @RequestMapping("/hello")
    Object hello() {
        return "hello world";
    }

    @RequestMapping("/json/out")
    public Object objectToJson() {
        Demo demo = new Demo();
        demo.setCreateTime(LocalDateTime.now());
        demo.setUpdateTime(LocalDateTime.now());
        return demo;
    }

    @RequestMapping("/json/in")
    public Object JsonToObject(@RequestBody JSONObject jsonObject) {
        Demo demo = JSON.to(Demo.class, jsonObject);
        System.out.println(demo);
        return demo;
    }

    @RequestMapping("/test")
    public String JsonToObject() {
        return "http://gcjs.sczwfw.gov.cn/ele/?Form_Num=Z01";
    }

    @GetMapping("/test")
    public String JsonToObject1(@RequestBody JSONObject jsonObject) {
        return "http://gcjs.sczwfw.gov.cn/ele/?Form_Num=Z01";
    }
}