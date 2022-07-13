package com.jdw.springboot.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * freemaker建言类
 * 配置freemaker全局model实例属性
 * @author ListJiang
 * @since 2020/9/22 15:09
 */
@ControllerAdvice
public class FreemakerAdvice {
    @Value("${server.servlet.context-path}")
    private String contentPath;

    @ModelAttribute(name = "contentPath")
    public String contentPath() {
        return contentPath;
    }
}
