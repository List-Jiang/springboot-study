package com.jdw.springboot.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class CustomEventController {
    private final ApplicationContext applicationContext;

    @PostMapping("/test")
    Object test(@RequestBody Map map) {
        ApplicationEvent applicationEvent = new CustomEvent(map);
        applicationContext.publishEvent(applicationEvent);
        map.put("message", "事件发送成功");
        return map;
    }
}
