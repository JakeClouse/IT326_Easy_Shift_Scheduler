package com.EasyShiftScheduler.CalEnder.Repositories;


import com.EasyShiftScheduler.CalEnder.Entities.Employer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.EasyShiftScheduler.CalEnder.Entities.Employee;
import org.springframework.data.repository.query.Param;

import com.EasyShiftScheduler.CalEnder.Entities.Employer;

@Repository
public interface EmployerRepository extends CrudRepository<Employer, Long> {
    // TEST ASAP WHEN WE MAKE DB
    @Modifying
    @Query("update Employee u set u.schedule = null where u.id = :id")
    void deleteEmployeeSchedule(@Param("id") long id);

}
