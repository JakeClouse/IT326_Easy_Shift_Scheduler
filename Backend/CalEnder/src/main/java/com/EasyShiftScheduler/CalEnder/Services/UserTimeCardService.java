package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserTimeCardService {
    private final UserRepository userRepository;

    public UserTimeCardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String setWorkedHours(long userID, int workedHours) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            User gotUser = user.get();
            UserTimecard user_timecard = gotUser.getUser_timecard();
            user_timecard.setWorked_hours(workedHours);
            gotUser.setUser_timecard(user_timecard);
            userRepository.save(gotUser);
        }
        else {
            return "User not found";
        }

        return "User worked hours saved";
    }
}
