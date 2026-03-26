package com.EasyShiftScheduler.CalEnder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.EasyShiftScheduler.CalEnder.Repositories.TimeOffRequestRepository;

@Service
public class TimeOffRequestService {

    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @PostMapping
    public TimeOffRequest CreateTimeOffRequest(TimeOffRequest timeOffRequest) {
        return timeOffRequestRepository.save(timeOffRequest);
    }

    @PostMapping
    public TimeOffRequest ReadTimeOffRequest(Long id) {
        return timeOffRequestRepository.findById(id).orElse(null);
    }


    @PostMapping
    public TimeOffRequest UpdateTimeOffRequest(TimeOffRequest timeOffRequest, Long id) {
        return timeOffRequestRepository.save(timeOffRequest);
    }

    @DeleteMapping
    public void DeleteTimeOffRequest(Long id) {
        timeOffRequestRepository.deleteById(id);
    }
    
}


