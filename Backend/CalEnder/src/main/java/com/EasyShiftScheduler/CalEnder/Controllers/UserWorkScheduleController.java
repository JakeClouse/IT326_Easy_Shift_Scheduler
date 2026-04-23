package com.EasyShiftScheduler.CalEnder.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Services.UserWorkScheduleService;


@RestController
@RequestMapping("/api/schedule")
public class UserWorkScheduleController {

    private final UserWorkScheduleService userWorkScheduleService;

    public UserWorkScheduleController(UserWorkScheduleService userWorkScheduleService){
        this.userWorkScheduleService = userWorkScheduleService;
    }

    @PostMapping("/create-schedule")
    public UserWorkSchedule createSchedule(@RequestParam("userID") long userID, @RequestParam("times") List<LocalDateTime> times){
        return userWorkScheduleService.createSchedule(userID, times);
    }

    @PostMapping("/update-schedule")
    public UserWorkSchedule updateSchedule(@RequestParam("UserWorkScheduleID") long UserWorkScheduleID, @RequestParam("times") List<LocalDateTime> times){
        return userWorkScheduleService.updateSchedule(UserWorkScheduleID, times);
    }

}
