package com.EasyShiftScheduler.CalEnder.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.EasyShiftScheduler.CalEnder.Entities.Employee;
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    public void dropShift(Long id, String shift);
}
