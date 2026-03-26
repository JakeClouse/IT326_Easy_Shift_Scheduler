package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Repositories.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public Employer createEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    public Employer updateEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }

    public Employer getEmployer(Long id) {
        employerRepository.getById(id);
    }
}
