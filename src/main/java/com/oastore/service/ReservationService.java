package com.oastore.service;
import com.oastore.mapper.ReservationMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;
    private final RedissonClient redissonClient;

    public ReservationService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public boolean reserveClassroom(String name, LocalDateTime start, LocalDateTime end) {
        String lockKey = "reserveClassroom:" + name + ":" + start.toString() + ":" + end.toString();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试获取锁，等待5秒，锁定时间为10秒
            if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                try {
                    // 在这里检查是否可以预约，比如查数据库确认该时间段是否空闲
                    int count = reservationMapper.searchUsable(start,end);
                    if (count==0) {
                        // 进行预约逻辑
                        reservationMapper.updateTime(name, start, end);
                        return true;
                    } else {
                        return false; // 该时间段已被预约
                    }
                } finally {
                    lock.unlock(); // 释放锁
                }
            } else {
                return false; // 获取锁失败，可能并发导致
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false; // 出现异常，预约失败
        }
    }
}
