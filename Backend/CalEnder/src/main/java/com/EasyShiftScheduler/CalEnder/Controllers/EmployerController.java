package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    @Autowired
    EmployerService employerService;

    @PostMapping
    public Employer createEmployer(@RequestBody Employer employer) {
        return employerService.saveEmployer(employer);
    }

    @PostMapping
    public Employer updateEmployer(@RequestBody Employer employer) {
        return employerService.saveEmployer(employer.getId(), employer);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployer(@PathVariable Long id) {
        employerService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Employer getEmployer(@PathVariable Long id) {
        employerService.getById(id);
    }

}
