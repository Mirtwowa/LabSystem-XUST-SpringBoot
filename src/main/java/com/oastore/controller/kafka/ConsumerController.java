package com.oastore.controller.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class ConsumerController {
    @KafkaListener(groupId = "demo",topics = "myqueue")
    public void listen (ConsumerRecord<?, ?> record) throws Exception {
        System.out.println( "主题名："+record.topic());
        System.out.println( "偏移量："+record.offset());
        System.out.println( "消息："+record.value());
    }
}