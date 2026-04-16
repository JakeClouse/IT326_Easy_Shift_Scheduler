package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.Employee;
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

    @PostMapping("/{id}/{username}/{email}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable String username, @PathVariable String email) {
            return employeeService.updateEmployee(employee, username, email);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @DeleteMapping("/shifts/{id}")
    public void dropShift(@PathVariable Long id, @RequestParam String shift) {
        employeeService.dropShift(id, shift);
    }

}
