package com.jdw.springboot.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author ListJiang
 * @class kafka自定义配置参数类
 * @remark
 * @date 2020/7/2619:23
 */
//@ConfigurationProperties("kafka.topic")
@Data
public class KafKaConfig {
    private String groupId;
    private List<String> topicName;

}