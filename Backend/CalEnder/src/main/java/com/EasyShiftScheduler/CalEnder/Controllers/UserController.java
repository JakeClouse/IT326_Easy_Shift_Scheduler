package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PutMapping("/{userID}/account/update")
    public String updateAccountInfo(@PathVariable("userID") long userID, @RequestBody User user){
        return userService.updateAccountInfo(userID, user);
    }

    // Update compensation rate (employer action)
    @PutMapping("/{userID}/compensation-rate")
    public String updateCompensationRate(@PathVariable("userID") long userID, @RequestBody double newRate) {
        return userService.updateCompensationRate(userID, newRate);
    }

    // Get timecard
    @GetMapping("/{userID}/timecard")
    public UserTimecard getTimecard(@PathVariable("userID") long userID) {
        return userService.getTimecard(userID);
    }
  
    @PutMapping("/{userID}/password/update")
    public String updatePassword(@PathVariable("userID") long userID, @RequestBody String newPassword) {
        return userService.updatePassword(userID, newPassword);
    }

    @DeleteMapping("/{userID}/account/delete")
    public String deleteAccount(@PathVariable("userID") long userID) {
        return userService.deleteAccount(userID);
    }

    //User Timekeeping
    
    @PutMapping("/{userID}/time-off-request/submit")
    public String submitTimeOffRequest(@PathVariable("userID") long userID, @RequestBody UserTimecard timecard){
        return userService.submitTimeOffRequest(userID, timecard);
    }

    @PutMapping("/{userID}/timecard/update")
    public String overrideTimecard(@PathVariable("userID") long userID, @RequestBody UserTimecard userTimecard){
        return userService.overrideTimecard(userID, userTimecard);
    }

    // Generate compensation report for a specific user
    @GetMapping("/{userID}/compensation-report")
    public String generateCompensationReport(@PathVariable("userID") long userID) {
        return userService.generateCompensationReport(userID);
    }
}
