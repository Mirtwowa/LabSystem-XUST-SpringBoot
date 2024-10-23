package com.oastore.controller;


import com.oastore.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/classrooms")
public class ClassroomReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reserve")
    public String reserveClassroom(@RequestParam String name, @RequestParam String startTime, @RequestParam String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);
        boolean success = reservationService.reserveClassroom(name, start,end);
        return success ? "预约成功" : "预约失败，该时间段已被预约";
    }
}