package com.EasyShiftScheduler.CalEnder.Controllers;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Services.UserTimeCardService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user_timecard")
@AllArgsConstructor
public class UserTimeCardController {
    private UserTimeCardService userTimeCardService;

    @PutMapping("/worked_hours")
    public String updateWorkedHours(@RequestParam long userID, @RequestParam int worked_hours){
        return userTimeCardService.setWorkedHours(userID, worked_hours);
    }

    @PutMapping("/clockIn")
    public String clockIn(@RequestParam long userID, @RequestBody LocalDateTime time){
        return userTimeCardService.clockIn(userID, time);
    }

    @PutMapping("/clockOut")
    public String clockOut(@RequestParam long userID, @RequestBody LocalDateTime time, @RequestParam String reason){
        return userTimeCardService.clockOut(userID, time, reason);
    }
}
