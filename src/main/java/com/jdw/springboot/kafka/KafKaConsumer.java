package com.jdw.springboot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

/**
 * @author ListJiang
 * @class kafka消费者
 * @remark
 * @date 2020/7/2615:22
 */
@Slf4j
//@Component
public class KafKaConsumer {

    @KafkaListener(topics = "topic1")
    public void onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("简单消费：" + record.topic() + "-" + record.partition() + "-" + record.value());
//        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }


    @KafkaListener(topics = "testTopic")
    public void consumer(ConsumerRecord consumerRecord) {
        Optional<Object> kafkaMassage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMassage.isPresent()) {
            Object o = kafkaMassage.get();
            log.info("接收到的消息是：" + o);
        }

    }
}
