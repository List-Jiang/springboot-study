package com.jdw.springboot.kafka;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * kafka消息生产者
 * @author ListJiang
 * @since 2020/7/2615:06
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping(value = "/getAllUrl", method = RequestMethod.GET)
    public Set<String> getAllUrl() {
        // url与方法的对应关系
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<String> urls = new HashSet<>();
        handlerMethods.keySet().forEach(handlerMethod -> {
            assert handlerMethod.getPatternsCondition() != null;
            urls.addAll(handlerMethod.getPatternsCondition().getPatterns());
        });
        return urls;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody JSONObject jsonObject) {
        kafkaTemplate.send("topic1", jsonObject.toString());
        kafkaTemplate.send("testTopic", 1, "key", jsonObject.toString());
        return "OK";
    }

    @RequestMapping("/test")
    public String show() {
        kafkaTemplate.send("testTopic", "1", "你好");
        return "发送成功";
    }

    @RequestMapping("/test1")
    public String test1(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName());
            System.out.println(cookie.getValue());
            System.out.println(cookie.getPath());
        }
        return "发送成功";
    }
}

