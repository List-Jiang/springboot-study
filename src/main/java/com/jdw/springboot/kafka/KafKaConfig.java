package com.jdw.springboot.kafka;

import lombok.Data;

import java.util.List;

/**
 * @author 蒋德文
 * @class kafka自定义配置参数类
 * @since 2020/7/2619:23
 */
//@ConfigurationProperties("kafka.topic")
@Data
public class KafKaConfig {
    private String groupId;
    private List<String> topicName;

}
