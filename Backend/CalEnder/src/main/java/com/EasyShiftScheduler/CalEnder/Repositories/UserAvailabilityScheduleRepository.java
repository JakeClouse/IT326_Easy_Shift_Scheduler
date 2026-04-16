package com.EasyShiftScheduler.CalEnder.Repositories;

import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvailabilityScheduleRepository extends CrudRepository<UserAvailabilitySchedule, Long> {
}
