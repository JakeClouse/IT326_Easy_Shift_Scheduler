package com.EasyShiftScheduler.CalEnder.Repositories;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
