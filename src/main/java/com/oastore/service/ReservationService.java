package com.oastore.service; // 定义包名

import com.oastore.mapper.ReservationMapper; // 导入预约数据库操作接口
import org.redisson.api.RLock; // 导入分布式锁接口
import org.redisson.api.RedissonClient; // 导入Redisson客户端接口
import org.springframework.beans.factory.annotation.Autowired; // 导入Spring的自动注入注解
import org.springframework.stereotype.Service; // 导入服务组件注解

import java.time.LocalDateTime; // 导入时间处理类
import java.util.concurrent.TimeUnit; // 导入时间单位类

@Service // 表示该类是一个服务组件
public class ReservationService {

    @Autowired // 自动注入ReservationMapper
    private ReservationMapper reservationMapper;
    private final RedissonClient redissonClient; // 声明RedissonClient，最终不可更改

    public ReservationService(RedissonClient redissonClient) { // 构造函数，接收RedissonClient实例
        this.redissonClient = redissonClient; // 初始化成员变量
    }

    public boolean reserveClassroom(String name, LocalDateTime start, LocalDateTime end) { // 预约教室方法
        // 生成唯一的锁键，用于控制对该教室和时间段的访问
        String lockKey = "reserveClassroom:" + name + ":" + start.toString() + ":" + end.toString();
        RLock lock = redissonClient.getLock(lockKey); // 获取对应的分布式锁

        try {
            // 尝试获取锁，最多等待5秒，持有锁的时间为10秒
            if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                try {
                    // 检查给定时间段是否可用（即没有其他预约）
                    int count = reservationMapper.searchUsable(start, end);
                    if (count == 0) { // 如果该时间段可用
                        // 进行预约逻辑，将预约信息写入数据库
                        reservationMapper.updateTime(name, start, end);
                        return true; // 预约成功
                    } else {
                        return false; // 该时间段已被预约
                    }
                } finally {
                    lock.unlock(); // 释放锁
                }
            } else {
                return false; // 获取锁失败，表示可能并发导致的冲突
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            return false; // 出现异常，预约失败
        }
    }
}
