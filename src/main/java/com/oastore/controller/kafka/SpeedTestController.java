package com.oastore.controller.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeedTestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/test/speed")
    public String speedTest(@RequestParam int messageCount) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < messageCount; i++) {
            String msg = "测试消息 " + i;
            kafkaTemplate.send("your-topic-name", msg);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        return "发送 " + messageCount + " 条消息，耗时 " + duration + " 毫秒";
    }
}
