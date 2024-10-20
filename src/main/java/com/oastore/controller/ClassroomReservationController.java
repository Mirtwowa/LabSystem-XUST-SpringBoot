package com.oastore.controller;


import com.oastore.service.ClassroomReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/classrooms")
public class ClassroomReservationController {

    @Autowired
    private ClassroomReservationService reservationService;

    @PostMapping("/reserve")
    public String reserveClassroom(@RequestParam String classroomId, @RequestParam String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);

        boolean success = reservationService.reserveClassroom(classroomId, start);

        return success ? "预约成功" : "预约失败，该时间段已被预约";
    }
}