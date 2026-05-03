package com.EasyShiftScheduler.CalEnder.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Services.UserService;
import com.EasyShiftScheduler.CalEnder.Services.UserWorkScheduleService;


@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final UserWorkScheduleService userWorkScheduleService;
    private final UserService userService;

    public ScheduleController(UserWorkScheduleService userWorkScheduleService, UserService userService){
        this.userWorkScheduleService = userWorkScheduleService;
        this.userService = userService;
    }

    @PutMapping("/work-schedule/delete")
    public String deleteSchedule(@RequestParam long userID){
        return userService.deleteWorkSchedule(userID);
    }

    @PostMapping("/create-schedule")
    public UserWorkSchedule createSchedule(@RequestParam("userID") long userID, @RequestParam("times") List<LocalDateTime> times){
        return userWorkScheduleService.createSchedule(userID, times);
    }

    @PostMapping("/update-schedule")
    public UserWorkSchedule updateSchedule(@RequestParam("UserWorkScheduleID") long UserWorkScheduleID, @RequestParam("times") List<LocalDateTime> times){
        return userWorkScheduleService.updateSchedule(UserWorkScheduleID, times);
    }

    @PostMapping("/get-schedule")
    public String getSchedule(@RequestParam("UserWorkScheduleID") long UserWorkScheduleID){
        return userWorkScheduleService.getSchedule(UserWorkScheduleID);
    }

    @PostMapping("/acknowledge-schedule")
    public String acknowledgeSchedule(@RequestParam("UserID") long userID, @RequestParam("EmployerID") long employerID){
        return userWorkScheduleService.acknowledgeSchedule(userID, employerID);
    }

    @PutMapping("/avail-schedule/update")
    public String setAvailSchedule(@RequestParam long userID, @RequestBody UserAvailabilitySchedule availabilitySchedule){
        return userService.setAvailabilitySchedule(userID, availabilitySchedule);
    }

    @GetMapping("/avail-schedule")
    public String getAvailSchedule(@RequestParam("userID") long userID){
        return userService.getAvailabilitySchedule(userID);
    }

    // Create automatic schedule
    @PostMapping("/work-schedule/auto")
    public String createAutoSchedule(@RequestParam("userID") long userID) {
        return userService.createAutoSchedule(userID);
    }

    @PutMapping("/pickup-shift")
    public String pickupShift(@RequestParam long userID, @RequestParam("shiftID") long shiftID) {
        return userWorkScheduleService.pickupShift(userID, shiftID, null);
    }

    @PutMapping("/swap-shift")
    public String swapShift(@RequestParam("userID") long userID, @RequestParam("shiftID1") long shiftID, @RequestParam("shiftID2") List<LocalDateTime> shiftInOut) {
        return userWorkScheduleService.pickupShift(userID, shiftID, shiftInOut);
    }
}
