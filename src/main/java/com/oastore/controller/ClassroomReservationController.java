package com.oastore.controller;

import com.oastore.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/classrooms")
public class ClassroomReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveClassroom(
            @RequestParam String name,
            @RequestParam String startTime,
            @RequestParam String endTime
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start, end;
        try {
            start = LocalDateTime.parse(startTime, formatter);
            end = LocalDateTime.parse(endTime, formatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("时间格式错误，请使用yyyy-MM-dd HH:mm格式");
        }

        // 验证时间先后
        if (start.isAfter(end) || start.isEqual(end)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("开始时间不能晚于或等于结束时间");
        }

        boolean success = reservationService.reserveClassroom(name, start, end);
        if (success) {
            return ResponseEntity.ok("预约请求已发送，等待审核。");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("预约失败，该时间段已被预约");
        }
    }
}