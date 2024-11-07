package com.oastore.service;


import com.oastore.config.RabbitMqConfig;
import com.oastore.pojo.Bookstorage;
import com.oastore.pojo.User;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.utils.SystemTime;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@Service
public class RabbitMqService {
    @Autowired
    private RabbitMqConfig rabbitMqConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String REDIS_KEY_PREFIX = "UserMessage:";
    Integer index= 0;
    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME,concurrency = "3-10")
    @Transactional
    public void handleUserMessage(String message) {
        ValueOperations<String,String> operations = redisTemplate.opsForValue();
        // 2. 插入到Redis
        operations.set(REDIS_KEY_PREFIX + index++ , message,2, TimeUnit.MINUTES);
    }
}
