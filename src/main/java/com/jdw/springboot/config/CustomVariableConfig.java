package com.jdw.springboot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
//自定义参数空间，会自动扫描 custom 下的所有配置参数(默认支持驼峰转换)并尝试注入到该实体类的同名属性中。
@ConfigurationProperties("custom")
public class CustomVariableConfig {
    /*
    @Value("#{${custom.map}}")
    private Map<String,String> map;
    类使用ConfigurationProperties注解时失效，项目启动出错，但是该语法放入controller类下可以直接使用。
     */

    /**
     * 由于 已使用ConfigurationProperties注解，该 Value 注解可以忽略。
     * 而且，在ConfigurationProperties配置类下，Value注解的支持非常不友好，只支持基础类型注入，
     * 不建议混用。
     */
    @Value("${custom.customValue1}")
    private String customValue1;

    /**
     * 参数名默认驼峰转换 simple-view-controllers——>simpleViewControllers
     */
    private List<Map<String, String>> simpleViewControllers;

    /**
     * 设置默认值
     */
    @Value("#{'${custom.writeRegx:add.*,save.*,remove.*,update.*,batch.*,clear.*,add.*,append.*,modify.*,edit.*,insert.*,delete.*,do.*,creat.*}'.split(',')}:['']")
    private Set<String> writeRegx;

}
