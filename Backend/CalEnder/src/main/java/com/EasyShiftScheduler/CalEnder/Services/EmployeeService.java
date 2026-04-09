package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(user);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        return employeeRepository.save(id, user);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee getEmployee(Long id) {
        employeeRepository.getById(id);
    }

    public void dropShift(Long id, String shift) { employeeRepository.dropShift(id, shift); }

}
