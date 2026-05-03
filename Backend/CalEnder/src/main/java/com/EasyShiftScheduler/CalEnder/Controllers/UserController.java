package com.EasyShiftScheduler.CalEnder.Controllers;

import jakarta.persistence.Entity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String updateAccountInfo(@RequestParam long userID, @RequestParam String newUsername, @RequestParam String newEmail){
        return userService.updateAccountInfo(userID, newUsername, newEmail);
    }

    // Update compensation rate (employer action)
    @PutMapping("/compensation-rate")
    public String updateCompensationRate(@RequestParam long userID, @RequestParam double newRate) {
        return userService.updateCompensationRate(userID, newRate);
    }

    // Get user_timecard
    @GetMapping("/user_timecard")
    public String getTimecard(@RequestParam long userID) {
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

    @GetMapping("/account/get")
    public String getAccount(@RequestParam long userID){
        return userService.getAccount(userID);
    }

    @GetMapping("/getPunchByUser")
    public String getPunchByUser(@RequestParam long userId) {
        return userService.getPunchByUser(userId);
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

    @GetMapping("/all-users")
    public String getAllUsers() {
        return userService.getAllUsers();
    }
}
