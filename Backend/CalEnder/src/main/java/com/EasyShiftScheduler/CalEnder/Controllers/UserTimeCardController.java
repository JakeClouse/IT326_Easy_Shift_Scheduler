package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Services.UserTimeCardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
