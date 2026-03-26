package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Services.AvailabilityScheduleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/api/availabilitySchedule")
public class AvailabilityScheduleController {

    @Autowired
    private AvailabilityScheduleService availabilityScheduleService;

    @PostMapping
    public AvailabilitySchedule CreateAvailabilitySchedule(@RequestBody AvailabilitySchedule availabilitySchedule) {
        return availabilityScheduleService.CreateAvailabilitySchedule(availabilitySchedule);
    }

    @GetMapping("/{id}")
    public AvailabilitySchedule ReadAvailabilitySchedule(@PathVariable long id) {
        return availabilityScheduleService.ReadAvailabilitySchedule(id);
    }

    @PostMapping("/{id}")
    public AvailabilitySchedule UpdateAvailabilitySchedule(@RequestBody AvailabilitySchedule availabilitySchedule, @PathVariable long id) {
        return availabilityScheduleService.UpdateAvailabilitySchedule(availabilitySchedule, id);
    }

    @DeleteMapping("/{id}")
    public void DeleteAvailabilitySchedule(@PathVariable long id) {
        availabilityScheduleService.DeleteAvailabilitySchedule(id);
    }
      
}
