package com.EasyShiftScheduler.CalEnder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.EasyShiftScheduler.CalEnder.Repositories.AvailabilityScheduleRepository;

@Service
public class AvailabilityScheduleService {

    @Autowired
    private AvailabilityScheduleRepository availabilityScheduleRepository;

    public AvailabilitySchedule CreateAvailabilitySchedule(AvailabilitySchedule availabilitySchedule) {
        return availabilityScheduleRepository.save(availabilitySchedule);
    }

    public AvailabilitySchedule ReadAvailabilitySchedule(Long id) {
        return availabilityScheduleRepository.findById(id).orElse(null);
    }

    public AvailabilitySchedule UpdateAvailabilitySchedule(AvailabilitySchedule availabilitySchedule, Long id) {
        return availabilityScheduleRepository.save(availabilitySchedule);
    }

    public void DeleteAvailabilitySchedule(Long id) {
        availabilityScheduleRepository.deleteById(id);
    }
    
}
