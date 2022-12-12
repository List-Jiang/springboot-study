package com.jdw.springboot.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * @author 蒋德文
 * @since 2022/1/18
 * description 自定义事件监听器配置类
 */
@Slf4j
@Configuration
public class CustomEventListenerConfig {

    @EventListener
    public void customListener(CustomEvent event) {
        log.debug("注解方式监听器收到事件：" + event);
    }

    @EventListener
    public void customListener(ServletRequestHandledEvent event) {
        log.debug("请求方式:{}", event.getMethod());
        log.debug("请求客户端:{}", event.getClientAddress());
        log.debug("处理时间：{}ms", event.getProcessingTimeMillis());
    }
}