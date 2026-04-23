package com.EasyShiftScheduler.CalEnder.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserWorkScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserWorkScheduleService {
    
    private final UserWorkScheduleRepository userWorkScheduleRepository;
    private final UserRepository userRepository;

    public UserWorkScheduleService(UserWorkScheduleRepository userWorkScheduleRepository, UserRepository userRepository){
        this.userWorkScheduleRepository = userWorkScheduleRepository;
        this.userRepository = userRepository;
    }


    public UserWorkSchedule createSchedule(Long userID, List<LocalDateTime> times){
        Optional<User> user = userRepository.findById(userID);
        UserWorkSchedule uws = new UserWorkSchedule();

        if (times.size() % 2 != 0){
            uws.setWork_schedule(times);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Times are not properly formatted, schedule not saved");
        }
        
        if(user.isEmpty()){
            throw new EntityNotFoundException("User could not be found");
        }

        UserWorkSchedule returnObject = userWorkScheduleRepository.save(uws);
        user.get().setWork_schedule(returnObject);
        
        userRepository.save(user.get());

        return returnObject;
    }


    public UserWorkSchedule updateSchedule(long UserWorkScheduleID, List<LocalDateTime> times){
        Optional<UserWorkSchedule> uws = userWorkScheduleRepository.findById(UserWorkScheduleID);

        if (uws.isEmpty()){
            throw new EntityNotFoundException("Schedule could not be found");
        }

        if (times.size() % 2 != 0){
            uws.get().setWork_schedule(times);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Times are not properly formatted, schedule not saved");
        }   

        return userWorkScheduleRepository.save(uws.get());
    }


}
