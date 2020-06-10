package com.jdw.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author ListJiang
 * @class 自定义参数配置类
 * @remark 用于向spring注册自定义参数
 * @date 2020/6/1010:15
 */
// 标明这是一个配置类
@Configuration
// lombok 实体注解
@Data
public class CustomVariableConfig {
    //从 application 配置文件中获取 custom.value1 的值 “测试参数1” 注入到 customValue1。
    @Value("${custom.value1}")
    private String customValue1;
}
