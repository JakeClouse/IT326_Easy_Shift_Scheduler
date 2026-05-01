package com.EasyShiftScheduler.CalEnder.Services;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.web.server.ResponseStatusException;

import com.EasyShiftScheduler.CalEnder.Entities.Notifications.EmailDetails;
import com.EasyShiftScheduler.CalEnder.Entities.DroppedShift;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Repositories.DroppedShiftsRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserWorkScheduleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserWorkScheduleService {
    
    private final UserWorkScheduleRepository userWorkScheduleRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final DroppedShiftsRepository droppedShiftRepository;

    public UserWorkScheduleService(UserWorkScheduleRepository userWorkScheduleRepository, UserRepository userRepository, EmailService emailService, DroppedShiftsRepository droppedShiftRepository){
        this.userWorkScheduleRepository = userWorkScheduleRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.droppedShiftRepository = droppedShiftRepository;
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

        EmailDetails details = new EmailDetails(employer.get().getEmail(), message, "Schedule Acknowledgement: " + username);
        byte[] payload = SerializationUtils.serialize(details);

        return emailService.sendNotification(payload);
    }


    public String pickupShift(long userID, long droppedShiftID, List<LocalDateTime> shiftToSwap) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty()) {
            return "User not found";
        }
        UserWorkSchedule schedule = user.get().getWork_schedule();
        Optional<DroppedShift> droppedShift = droppedShiftRepository.findById(droppedShiftID);
        if (droppedShift.isEmpty()) {
            return "Shift not found";
        }
        LocalDateTime droppedShiftStart = droppedShift.get().getStartDate();
        LocalDateTime droppedShiftEnd = droppedShift.get().getEndDate();


        if (shiftToSwap != null){
            if (shiftToSwap.size() != 2){
                return "Shift times are not properly formatted";
            }
            shiftToSwap.sort(Comparator.naturalOrder());;
            LocalDateTime swappingShiftStart = shiftToSwap.get(0);
            LocalDateTime swappingShiftEnd = shiftToSwap.get(1);
            List<LocalDateTime> swapperSchedule = schedule.getWork_schedule();
            for (int i = 0; i < swapperSchedule.size(); i += 2) {
                LocalDateTime scheduledStart = swapperSchedule.get(i);
                LocalDateTime scheduledEnd = swapperSchedule.get(i + 1);
                if ((swappingShiftStart.isBefore(scheduledEnd) && (swappingShiftStart.isAfter(scheduledStart))||(swappingShiftStart.isEqual(scheduledStart))) ||
                    ((swappingShiftEnd.isBefore(scheduledEnd)||swappingShiftEnd.isEqual(scheduledEnd)) && swappingShiftEnd.isAfter(scheduledStart))) {//TODO Fix equality condition
                    swapperSchedule.add(swappingShiftStart);
                    swapperSchedule.add(swappingShiftEnd);
                    swapperSchedule.sort(Comparator.naturalOrder());
                    for (LocalDateTime shiftTime : swapperSchedule){
                        if(droppedShiftStart.isBefore(shiftTime)&&droppedShiftEnd.isAfter(scheduledEnd)){
                            return "Shift Conflitcts with another on the schedule";
                        }
                    }
                    




                }
            }
            return "User is not scheduled during shift provided";









        }else {
            List<LocalDateTime> newSchedule = schedule.getWork_schedule();
            newSchedule.add(shiftStart);
            newSchedule.add(shiftEnd);
            schedule.setWork_schedule(newSchedule);
            userWorkScheduleRepository.save(schedule);
            droppedShiftRepository.delete(droppedShift.get());
            return "Shift picked up successfully: " + shiftStart.toString() + " to " + shiftEnd.toString();
        }

    }

    public String swapShift(long userID, long shiftID, List<LocalDateTime> shiftInOut) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        UserWorkSchedule schedule = user.get().getWork_schedule();
        if (schedule == null) {
            throw new EntityNotFoundException("User does not have a work schedule");
        }

        return schedule.swapShift(shiftID, shiftInOut, user.get());
    }
}
