package com.EasyShiftScheduler.CalEnder.Repositories;

import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkScheduleRepository extends JpaRepository<UserWorkSchedule, Long> {
}
