package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Services.EmployeeScheduleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/api/employeeSchedule")
public class EmployeeScheduleController {

    @Autowired
    private EmployeeScheduleService employeeScheduleService;

    @PostMapping
    public EmployeeSchedule CreateEmployeeSchedule(@RequestBody EmployeeSchedule employeeSchedule) {
        return employeeScheduleService.CreateEmployeeSchedule(employeeSchedule);
    }

    @GetMapping("/{id}")
    public EmployeeSchedule ReadEmployeeSchedule(@PathVariable long id) {
        return employeeScheduleService.ReadEmployeeSchedule(id);
    }

    @PostMapping("/{id}")
    public EmployeeSchedule UpdateEmployeeSchedule(@RequestBody EmployeeSchedule employeeSchedule, @PathVariable long id) {
        return employeeScheduleService.UpdateEmployeeSchedule(employeeSchedule, id);
    }

    @DeleteMapping("/{id}")
    public void DeleteEmployeeSchedule(@PathVariable long id) {
        employeeScheduleService.DeleteEmployeeSchedule(id);
    }

    public void removeEmployeeShift(Long id, LocalDateTime startTime) {  employeeScheduleService.removeEmployeeShift(id, startTime); }

}
