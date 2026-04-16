package com.EasyShiftScheduler.CalEnder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyShiftScheduler.CalEnder.Entities.Employer;
import com.EasyShiftScheduler.CalEnder.Repositories.EmployerRepository;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public Employer createEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    public Employer updateEmployer(Employer employer, String username, String email) {
        employer.getUserOperations().updateProfile(employer, username, email);
        return employerRepository.save(employer);
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }

    public Employer getEmployer(Long id) {
        return employerRepository.findById(id).orElse(null);
    }

    public void deleteEmployeeSchedule(Long id) {
        employerRepository.deleteEmployeeSchedule(id);
    }
}
