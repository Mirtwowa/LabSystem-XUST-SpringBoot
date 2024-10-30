package com.oastore.Exception;

import java.time.LocalDateTime;

public class TruncateToHalfHour {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime truncated = truncateToHalfHour(now);
        System.out.println("Original: " + now);
        System.out.println("Truncated: " + truncated);
    }

    public static LocalDateTime truncateToHalfHour(LocalDateTime dateTime) {
        long minutes = dateTime.getMinute();
        long truncatedMinutes = (minutes / 30) * 30; // 向下舍入到最近的30分钟
        return dateTime.withMinute((int) truncatedMinutes)
                .withSecond(0)
                .withNano(0); // 截断到分钟，秒和纳秒设置为0
    }
}