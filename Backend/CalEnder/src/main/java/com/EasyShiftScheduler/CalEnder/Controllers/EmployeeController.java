package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PostMapping
    public Employee updateEmployee(@RequestBody Employee employee) {
            return employeeService.saveEmployee(employee.getId(), employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        employeeService.getById(id);
    }

    public void dropShift(Long id, String shift) { employeeService.dropShift(id, shift); }

}
