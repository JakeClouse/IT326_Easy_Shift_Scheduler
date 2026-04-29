package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Services.EmailService;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private EmailService emailService;

    @PutMapping("/{userID}/work-schedule/delete")
    public String deleteSchedule(@PathVariable("userID") long userID){
        return userService.deleteWorkSchedule(userID);
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

    // Create automatic schedule
    @PostMapping("/{userID}/work-schedule/auto")
    public String createAutoSchedule(@PathVariable("userID") long userID) {
        return userService.createAutoSchedule(userID);
    }

    // Generate compensation report
    @GetMapping("/{userID}/compensation-report")
    public String generateCompensationReport(@PathVariable("userID") long userID) {
        return userService.generateCompensationReport(userID);
    }

    // Update compensation rate (employer action)
    @PutMapping("/{userID}/compensation-rate")
    public String updateCompensationRate(@PathVariable("userID") long userID, @RequestBody double newRate) {
        return userService.updateCompensationRate(userID, newRate);
    }

    // Get timecard
    @GetMapping("/{id}/timecard")
    public UserTimecard getTimecard(@RequestParam("userID") long userID) {
        return userService.getTimecard(userID);
    }
  
    // Update compensation rate (employer action)
    @PutMapping("/{userID}/compensation-rate")
    public String updatePassword(@PathVariable("userID") long userID, @RequestBody String newPassword) {
        return userService.updatePassword(userID, newPassword);
    }

    @DeleteMapping("/{userID}/account/delete")
    public String deleteAccount(@PathVariable("userID") long userID) {
        return userService.deleteAccount(userID);
    }

    @PutMapping("/{userID}/groups/join/{groupID}")
    public String deleteAccount(@PathVariable long userID, @PathVariable long groupID) {
        return userService.joinGroup(userID, groupID);
    }
}
