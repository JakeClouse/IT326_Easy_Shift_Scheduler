package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.TimeOffRequest;
import com.EasyShiftScheduler.CalEnder.Services.TimeOffRequestService;

@RestController
@RequestMapping("/api/time-off-request")
public class TimeOffRequestController {
    private TimeOffRequestService timeOffRequestService;

    public TimeOffRequestController(TimeOffRequestService timeOffRequestService) {
        this.timeOffRequestService = timeOffRequestService;
    }

    @PostMapping("/request-time-off")
    public String requestTimeOff(@RequestParam long userID, @RequestBody TimeOffRequest timeOffRequest) {
        return timeOffRequestService.requestTimeOff(userID, timeOffRequest);
    }

    @GetMapping("/view-time-off-requests")
    public String getTimeOffRequests() {
        return timeOffRequestService.getTimeOffRequests();
    }

    @PutMapping("/approve")
    public String approveTimeOffRequest(@RequestParam long requestID) {
        return timeOffRequestService.approveTimeOffRequest(requestID);
    }

    @PutMapping("/deny")
    public String denyTimeOffRequest(@RequestParam long requestID) {
        return timeOffRequestService.denyTimeOffRequest(requestID);
    }
}
