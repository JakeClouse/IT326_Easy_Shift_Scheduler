package com.EasyShiftScheduler.CalEnder.Repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository	
public interface TimeOffRequestRepository extends CrudRepository<TimeOffRequest, Long> {
}
    