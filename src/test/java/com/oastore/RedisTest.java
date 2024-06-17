package com.oastore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void id(){
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.set("username","zhangsan");
        operations.set("id","1",15, TimeUnit.SECONDS);
    }
    @Test
    public void getUsername() {
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));
    }
}
