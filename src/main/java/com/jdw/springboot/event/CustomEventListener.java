package com.jdw.springboot.event;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 显示实现 ApplicationListener 接口的方式创立监听器
 *
 * @author 蒋德文
 * @since : 2022/1/18
 */
@Slf4j
@Component
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(@NotNull CustomEvent event) {
        log.debug("实现类方式监听器收到事件：" + event);
    }
}