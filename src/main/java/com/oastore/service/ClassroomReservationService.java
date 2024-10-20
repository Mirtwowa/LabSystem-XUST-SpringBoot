package com.oastore.service;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class ClassroomReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ClassroomReservationService.class);

    @Autowired
    private RedissonClient redissonClient;

    private static final long LOCK_EXPIRE_TIME = 10; // 锁的超时时间，单位：秒
    private static final long LOCK_WAIT_TIME = 5; // 尝试获取锁的最长等待时间，单位：秒

    public boolean reserveClassroom(String classroomId, LocalDateTime startTime) {
        // 截断到半小时的逻辑
        long truncatedMinute = startTime.getMinute() / 30 * 30;
        LocalDateTime truncatedStartTime = startTime.withMinute((int) truncatedMinute).withSecond(0).withNano(0);

        String lockKey = "classroom:lock:" + classroomId + ":" + truncatedStartTime;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.tryLock(LOCK_WAIT_TIME, LOCK_EXPIRE_TIME, TimeUnit.SECONDS)) {
                try {
                    // 检查当前时间段是否已被预约（这里需要实现具体逻辑，比如查询数据库）
                    // 如果未被预约，则进行预约操作（这里需要实现具体逻辑，比如更新数据库）

                    // 假设预约成功
                    logger.info("Successfully reserved classroom {} for start time {}", classroomId, truncatedStartTime);
                    return true;
                } finally {
                    lock.unlock(); // 确保在finally块中释放锁
                }
            } else {
                logger.warn("Failed to acquire lock for classroom {} at start time {}. Another reservation may be in progress.", classroomId, truncatedStartTime);
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            logger.error("Thread was interrupted while trying to acquire lock for classroom {} at start time {}.", classroomId, truncatedStartTime, e);
            return false; // 处理中断异常，返回预约失败
        } catch (Exception e) {
            // 捕获其他可能的异常，比如RedissonClient相关的异常，这里可以添加更详细的日志或错误处理逻辑
            logger.error("An error occurred while trying to reserve classroom {} for start time {}.", classroomId, truncatedStartTime, e);
            return false;
        }
    }
}