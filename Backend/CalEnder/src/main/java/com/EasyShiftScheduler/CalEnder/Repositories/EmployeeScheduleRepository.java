package com.EasyShiftScheduler.CalEnder.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.EasyShiftScheduler.CalEnder.Entities.EmployeeSchedule;

@Repository	
public interface EmployeeScheduleRepository extends CrudRepository<EmployeeSchedule, Long> {
}
