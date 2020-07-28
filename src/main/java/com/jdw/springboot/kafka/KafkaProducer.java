package com.jdw.springboot.kafka;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ListJiang
 * @class kafka消息生产者
 * @remark
 * @date 2020/7/2615:06
 */
//标明该类是遵循restful风格的controller。相当于给所有mapping添加了@RequestBody注解
@Controller
@RequestMapping("/kafka")
@ResponseBody
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/getAllUrl", method = RequestMethod.GET)
    public Set<String> getAllUrl() {
        // url与方法的对应关系
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<String> urls = new HashSet<>();
        handlerMethods.keySet().forEach(handlerMethod -> urls.addAll(handlerMethod.getPatternsCondition().getPatterns()));
        return urls;
    }
    @GetMapping("/send")
    public String sendMessage(@RequestBody JSONObject jsonObject){
        kafkaTemplate.send("topic1", jsonObject.toString());
        kafkaTemplate.send("topic1",new Integer(3),"key",jsonObject.toString());
        return "OK";
    }
    @RequestMapping("/test")
    public String show() {
        kafkaTemplate.send("testTopic","1", "你好");
        return "发送成功";
    }
}
