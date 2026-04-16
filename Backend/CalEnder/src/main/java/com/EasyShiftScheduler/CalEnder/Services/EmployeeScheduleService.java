package com.EasyShiftScheduler.CalEnder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.EasyShiftScheduler.CalEnder.Repositories.EmployeeScheduleRepository;

@Service
public class EmployeeScheduleService {

    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;

    public EmployeeSchedule CreateEmployeeSchedule(EmployeeSchedule employeeSchedule) {
        return employeeScheduleRepository.save(employeeSchedule);
    }

    public EmployeeSchedule ReadEmployeeSchedule(Long id) {
        return employeeScheduleRepository.findById(id).orElse(null);
    }

    public EmployeeSchedule UpdateEmployeeSchedule(EmployeeSchedule employeeSchedule, Long id) {
        return employeeScheduleRepository.save(employeeSchedule);
    }

    public void DeleteEmployeeSchedule(Long id) {
        employeeScheduleRepository.deleteById(id);
    }

    public void removeEmployeeShift(Long id, LocalDateTime startTime) { employeeScheduleRepository.removeEmployeeShift(id, startTime); }
    
}
