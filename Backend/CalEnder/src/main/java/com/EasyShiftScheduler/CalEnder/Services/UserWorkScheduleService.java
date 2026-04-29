package com.EasyShiftScheduler.CalEnder.Services;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Entities.Notifications.EmailDetails;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserWorkScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserWorkScheduleService {
    
    private final UserWorkScheduleRepository userWorkScheduleRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserWorkScheduleService(UserWorkScheduleRepository userWorkScheduleRepository, UserRepository userRepository, EmailService emailService){
        this.userWorkScheduleRepository = userWorkScheduleRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
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

        UserWorkSchedule saved = userWorkScheduleRepository.save(uws.get());

        try {
            User user = saved.getUser();
            if (user != null) {
                EmailDetails details = new EmailDetails(user.getEmail(), "Your schedule has been updated: " + saved.getWork_schedule().toString(), "Schedule Update");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(details);
                emailService.sendNotification(baos.toByteArray());
            }
        } catch (Exception e) {
            System.out.println("Failed to send notification: " + e.getMessage());
        }

        return saved;
    }

    public String acknowledgeSchedule(long userID, long employerID){
        Optional<User> employer = userRepository.findById(employerID);
        Optional<User> user = userRepository.findById(userID);

        if (employer.isEmpty()){
            throw new EntityNotFoundException("Recipient Not Found");
        }

        if (user.isEmpty()){
            throw new EntityNotFoundException("Sender Not Found");
        }

        String username = user.get().getUsername();

        String message = username + " has acknowledged their schedule";

        //send email here
        return "Email Sent";
    }


}
