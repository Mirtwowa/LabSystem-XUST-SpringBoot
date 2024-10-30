package com.oastore.controller.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerController {

    private long totalProcessingTime = 0; // 累计处理时间
    private int messageCount = 0; // 处理的消息数量

    @KafkaListener(groupId = "demo", topics = "myqueue")
    public void listen(ConsumerRecord<?, ?> record) {
        long startTime = System.currentTimeMillis();

        System.out.println("主题名：" + record.topic());
        System.out.println("偏移量：" + record.offset());
        System.out.println("消息：" + record.value());

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        totalProcessingTime += duration; // 累计处理时间
        messageCount++; // 增加处理的消息数量

        System.out.println("处理时间：" + duration + " 毫秒");

        // 每处理200条消息输出一次总时间
        if (messageCount % 500 == 0) {
            System.out.println("处理500条消息的总时间：" + totalProcessingTime + " 毫秒");
            System.out.println("当前处理的消息数量：" + messageCount);
            // 重置计时器
            totalProcessingTime = 0;
        }
    }
}
