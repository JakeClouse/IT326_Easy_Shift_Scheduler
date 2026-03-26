package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Services.TimeOffRequestService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/timeOffRequest")
public class TimeOffRequestController {

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @PostMapping
    public TimeOffRequest CreateTimeOffRequest(@RequestBody TimeOffRequest timeOffRequest) {
        return timeOffRequestService.CreateTimeOffRequest(timeOffRequest);
    }

    @GetMapping("/{id}")
    public TimeOffRequest ReadTimeOffRequest(@PathVariable long id) {
        return timeOffRequestService.ReadTimeOffRequest(id);
    }

    @PostMapping("/{id}")
    public TimeOffRequest UpdateTimeOffRequest(@RequestBody TimeOffRequest timeOffRequest, @PathVariable long id) {
        return timeOffRequestService.UpdateTimeOffRequest(timeOffRequest, id);
    }

    @DeleteMapping("/{id}")
    public void DeleteTimeOffRequest(@PathVariable long id) {
        timeOffRequestService.DeleteTimeOffRequest(id);
    }
      
}

