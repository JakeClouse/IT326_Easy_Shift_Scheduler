package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.TimeOffRequest;
import com.EasyShiftScheduler.CalEnder.Repositories.TimeOffRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class TimeOffRequestService {
    private TimeOffRequestRepository TimeOffRequestRepository;

    public TimeOffRequestService(TimeOffRequestRepository TimeOffRequestRepository){
        this.TimeOffRequestRepository = TimeOffRequestRepository;
    }

    public TimeOffRequest assignReason(Long id, String reason) {
        TimeOffRequest TimeOffRequest = TimeOffRequestRepository.findById(id).orElse(null);
        if (TimeOffRequest != null) {
            TimeOffRequest.setReason(reason);
            return TimeOffRequestRepository.save(TimeOffRequest);
        }
        return null;
    }
    
}
