package com.EasyShiftScheduler.CalEnder.Repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository	
public interface PunchRepository extends CrudRepository<Punch, Long> {

}
    