package com.EasyShiftScheduler.CalEnder.Repositories;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PunchRepository extends JpaRepository<Punch, Long> {
}
