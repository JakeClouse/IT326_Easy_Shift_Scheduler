package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
            UserTimecard timecard = gotUser.getTimecard();
            timecard.setWorked_hours(workedHours);
            gotUser.setTimecard(timecard);
            userRepository.save(gotUser);
        }
        else {
            return "User not found";
        }

        return "User worked hours saved";
    }

    public String clockIn(long userID, LocalDateTime time) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            User gotUser = user.get();
            UserTimecard timecard = gotUser.getTimecard();
            Punch punchIn = new Punch();
            punchIn.setPunch_time(time);
            punchIn.setReason("Clock-in");
            List<Punch> punchList = timecard.getPunch_times();
            punchList.add(punchIn);
            timecard.setPunch_times(punchList);
            gotUser.setTimecard(timecard);
            userRepository.save(gotUser);
        }
        else {
            return "User not found";
        }

        return "Employee has clocked in";
    }

    public String clockOut(long userID, LocalDateTime time, String reason) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            User gotUser = user.get();
            UserTimecard timecard = gotUser.getTimecard();
            Punch punchOut = new Punch();
            punchOut.setPunch_time(time);
            punchOut.setReason(reason);
            List<Punch> punchList = timecard.getPunch_times();
            punchList.add(punchOut);
            timecard.setPunch_times(punchList);
            gotUser.setTimecard(timecard);
            userRepository.save(gotUser);
        } else {
            return "User not found";
        }

        return "Employee has clocked out";
    }
}
