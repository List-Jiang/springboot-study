package com.jdw.springboot.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ListJiang
 * @class kafka消费者
 * @remark
 * @date 2020/7/2615:22
 */
//举手交给spring管理
@Component
public class KafKaConsumer {

    @KafkaListener(topics = "topic1")
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }


    @KafkaListener(topics = "testTopic")
    public void consumer(ConsumerRecord consumerRecord){
        Optional<Object> kafkaMassage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMassage.isPresent()){
            Object o = kafkaMassage.get();
            System.out.println("接收到的消息是："+o);
        }

    }
}
