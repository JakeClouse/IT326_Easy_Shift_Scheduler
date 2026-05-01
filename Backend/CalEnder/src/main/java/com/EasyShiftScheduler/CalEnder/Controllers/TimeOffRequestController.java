package com.EasyShiftScheduler.CalEnder.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/{id}/request-time-off")
    public String requestTimeOff(@PathVariable("id") long userID, @RequestBody TimeOffRequest timeOffRequest) {
        return timeOffRequestService.requestTimeOff(userID, timeOffRequest);
    }

    @GetMapping("/view-time-off-requests")
    public String getTimeOffRequests() {
        return timeOffRequestService.getTimeOffRequests();
    }

    @PutMapping("/{requestID}/approve")
    public String approveTimeOffRequest(@PathVariable("requestID") long requestID) {
        return timeOffRequestService.approveTimeOffRequest(requestID);
    }

    @PutMapping("/{requestID}/deny")
    public String denyTimeOffRequest(@PathVariable("requestID") long requestID) {
        return timeOffRequestService.denyTimeOffRequest(requestID);
    }
}
