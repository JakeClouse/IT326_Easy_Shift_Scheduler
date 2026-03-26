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

    @PostMapping
    public EmployeeSchedule CreateEmployeeSchedule(EmployeeSchedule employeeSchedule) {
        return employeeScheduleRepository.save(employeeSchedule);
    }

    @PostMapping
    public EmployeeSchedule ReadEmployeeSchedule(Long id) {
        return employeeScheduleRepository.findById(id).orElse(null);
    }


    @PostMapping
    public EmployeeSchedule UpdateEmployeeSchedule(EmployeeSchedule employeeSchedule, Long id) {
        return employeeScheduleRepository.save(employeeSchedule);
    }

    @DeleteMapping
    public void DeleteEmployeeSchedule(Long id) {
        employeeScheduleRepository.deleteById(id);
    }
    
}
