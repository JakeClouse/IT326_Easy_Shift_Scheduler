package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PutMapping("/{userID}/work-schedule/delete")
    public String deleteSchedule(@PathVariable("userID") long userID){
        return userService.deleteSchedule(userID);
    }

    @PutMapping("/{userID}/avail-schedule/update")
    public String setAvailSchedule(@PathVariable("userID") long userID, @RequestBody UserAvailabilitySchedule availabilitySchedule){
        return userService.setAvailabilitySchedule(userID, availabilitySchedule);
    }

    @GetMapping("/{userID}/avail-schedule")
    public String getAvailSchedule(@PathVariable("userID") long userID){
        return userService.getAvailabilitySchedule(userID);
    }

    @PutMapping("/{userID}/time-off-request/submit")
    public String submitTimeOffRequest(@PathVariable("userID") long userID, @RequestBody UserTimecard timecard){
        return userService.submitTimeOffRequest(userID, timecard);
    }

    @PutMapping("/{userID}/account/update")
    public String updateAccountInfo(@PathVariable("userID") long userID, @RequestBody User user){
        return userService.updateAccountInfo(userID, user);
    }

    @PutMapping("/{userID}/timecard/update")
    public String overrideTimecard(@PathVariable("userID") long userID, @RequestBody UserTimecard userTimecard){
        return userService.overrideTimecard(userID, userTimecard);
    }

}
