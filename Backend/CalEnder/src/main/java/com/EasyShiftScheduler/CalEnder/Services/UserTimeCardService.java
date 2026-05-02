package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserTimecard;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserTimecardRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class UserTimeCardService {
    private final UserRepository userRepository;

    public UserTimeCardService(UserRepository userRepository, UserTimecardRepository timecardRepository) {
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

    public String clockIn(long userID, LocalDateTime time) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            User gotUser = user.get();
            UserTimecard timecard = gotUser.getUser_timecard();
            List<Punch> punchList = timecard.getPunch_times();
            if (!punchList.isEmpty() && punchList.get(punchList.size() - 1).reason.equals("Clock-in")) {
                return "Error: Employee is already clocked in";
            }
            Punch punchIn = new Punch();
            punchIn.setPunch_time(time);
            punchIn.setReason("Clock-in");
            punchList.add(punchIn);
            timecard.setPunch_times(punchList);
            gotUser.setUser_timecard(timecard);
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
            UserTimecard timecard = gotUser.getUser_timecard();
            List<Punch> punchList = timecard.getPunch_times();
            if(punchList.isEmpty() || !punchList.get(punchList.size() - 1).reason.equals("Clock-in")) {
                return "Error: Employee is not clocked in";
            }
            Punch punchOut = new Punch();
            punchOut.setPunch_time(time);
            punchOut.setReason(reason);
            Duration elapsed = Duration.between(punchOut.getPunch_time(), punchList.get(punchList.size() - 1).getPunch_time());
            punchList.add(punchOut);
            timecard.setPunch_times(punchList);
            timecard.setWorked_hours(elapsed.toMinutes()/60.0);
            gotUser.setUser_timecard(timecard);
            userRepository.save(gotUser);
        } else {
            return "User not found";
        }

        return "Employee has clocked out";
    }
}
