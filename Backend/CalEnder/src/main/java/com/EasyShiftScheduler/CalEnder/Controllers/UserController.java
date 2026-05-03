package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.web.bind.annotation.*;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Services.EmailService;
import com.EasyShiftScheduler.CalEnder.Services.UserService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private EmailService emailService;

    @PutMapping("/account/update")
    public String updateAccountInfo(@RequestBody long userID, @RequestBody User user){
        return userService.updateAccountInfo(userID, user);
    }

    // Update compensation rate (employer action)
    @PutMapping("/compensation-rate")
    public String updateCompensationRate(@RequestBody long userID, @RequestBody double newRate) {
        return userService.updateCompensationRate(userID, newRate);
    }

    // Get user_timecard
    @GetMapping("/user_timecard")
    public UserTimecard getTimecard(@RequestBody long userID) {
        return userService.getTimecard(userID);
    }
  
    @PutMapping("/password/update")
    public String updatePassword(@RequestParam long userID, @RequestBody String newPassword) {
        return userService.updatePassword(userID, newPassword);
    }

    @DeleteMapping("/account/delete")
    public String deleteAccount(@RequestParam long userID) {
        return userService.deleteAccount(userID);
    }

    //User Timekeeping
    
    @PutMapping("/time-off-request/submit")
    public String submitTimeOffRequest(@RequestParam long userID, @RequestBody UserTimecard user_timecard){
        return userService.submitTimeOffRequest(userID, user_timecard);
    }

    @PutMapping("/user_timecard/update")
    public String overrideTimecard(@RequestParam long userID, @RequestBody UserTimecard userTimecard){
        return userService.overrideTimecard(userID, userTimecard);
    }

    // Generate compensation report for a specific user
    @GetMapping("/compensation-report")
    public String generateCompensationReport(@RequestParam long userID) {
        return userService.generateCompensationReport(userID);
    }
}
