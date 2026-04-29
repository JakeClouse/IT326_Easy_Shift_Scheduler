package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PutMapping("/{id}/work-schedule/delete")
    public String deleteSchedule(@RequestParam("userID") long userID){
        return userService.deleteSchedule(userID);
    }

    @PutMapping("/{id}/avail-schedule/update")
    public String setAvailSchedule(@RequestParam("userID") long userID, @RequestBody UserAvailabilitySchedule availabilitySchedule){
        return userService.setSchedule(userID, availabilitySchedule);
    }

    @PutMapping("/{id}/account/update")
    public String updateAccountInfo(@RequestParam("userID") long userID, @RequestBody User user){
        return userService.updateAccountInfo(userID, user);
    }

    @PutMapping("/{id}/timecard/update")
    public String overrideTimecard(@RequestParam("userID") long userID, @RequestBody UserTimecard userTimecard){
        return userService.overrideTimecard(userID, userTimecard);
    }

    // Create automatic schedule
    @PostMapping("/{id}/work-schedule/auto")
    public String createAutoSchedule(@RequestParam("userID") long userID) {
        return userService.createAutoSchedule(userID);
    }

    // Generate compensation report
    @GetMapping("/{id}/compensation-report")
    public String generateCompensationReport(@RequestParam("userID") long userID) {
        return userService.generateCompensationReport(userID);
    }

    // Update compensation rate (employer action)
    @PutMapping("/{id}/compensation-rate")
    public String updateCompensationRate(@RequestParam("userID") long userID, @RequestBody double newRate) {
        return userService.updateCompensationRate(userID, newRate);
    }

    // Get timecard
    @GetMapping("/{id}/timecard")
    public UserTimecard getTimecard(@RequestParam("userID") long userID) {
        return userService.getTimecard(userID);
    }
}
