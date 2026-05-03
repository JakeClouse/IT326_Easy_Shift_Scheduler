package com.EasyShiftScheduler.CalEnder.Services;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.web.server.ResponseStatusException;

import com.EasyShiftScheduler.CalEnder.Entities.DroppedShift;
import com.EasyShiftScheduler.CalEnder.Entities.Notifications.EmailDetails;
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


    public String createSchedule(Long userID, List<LocalDateTime> times){
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

        return returnObject.toString();
    }


    public String updateSchedule(long UserWorkScheduleID, List<LocalDateTime> times){
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

        return saved.toString();
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


//PORTIONS OF THIS METHOD WERE WRITTEN WITH LLM ASSISTANCE.
    public String pickupShift(long userID, long droppedShiftID, List<LocalDateTime> shiftToSwap) {
        // Get the user accepting the shift
        Optional<User> acceptingUser = userRepository.findById(userID);
        if (acceptingUser.isEmpty()) {
            return "Accepting user not found";
        }

        // Get the dropped shift
        Optional<DroppedShift> droppedShiftOpt = droppedShiftRepository.findById(droppedShiftID);
        if (droppedShiftOpt.isEmpty()) {
            return "Dropped shift not found";
        }

        DroppedShift droppedShift = droppedShiftOpt.get();
        LocalDateTime droppedShiftStart = droppedShift.getStartDate();
        LocalDateTime droppedShiftEnd = droppedShift.getEndDate();
        
        // Get the original dropper's information
        User dropperUser = droppedShift.getUser_that_requested();
        Optional<User> dropperUserOpt = userRepository.findById(dropperUser.getId());
        if (dropperUserOpt.isEmpty()) {
            return "Original shift dropper not found";
        }

        UserWorkSchedule accepterSchedule = acceptingUser.get().getWork_schedule();
        UserWorkSchedule dropperSchedule = dropperUserOpt.get().getWork_schedule();
        List<LocalDateTime> accepterScheduleList = accepterSchedule.getWork_schedule();
        List<LocalDateTime> dropperScheduleList = dropperSchedule.getWork_schedule();

        LocalDateTime swapShiftStart = null;
        LocalDateTime swapShiftEnd = null;
        int accepterShiftIndex = -1;
        LocalDateTime foundShiftStart = null;
        LocalDateTime foundShiftEnd = null;

        // Handle swap shift if provided
        if (shiftToSwap != null) {
            if (shiftToSwap.size() != 2) {
                return "Shift times are not properly formatted";
            }

            shiftToSwap.sort(Comparator.naturalOrder());
            swapShiftStart = shiftToSwap.get(0);
            swapShiftEnd = shiftToSwap.get(1);

            // Validate that accepter is scheduled for the shift they're offering
            for (int i = 0; i < accepterScheduleList.size(); i += 2) {
                LocalDateTime scheduledStart = accepterScheduleList.get(i);
                LocalDateTime scheduledEnd = accepterScheduleList.get(i + 1);

                if ((swapShiftStart.isAfter(scheduledStart) || swapShiftStart.isEqual(scheduledStart)) &&
                    (swapShiftEnd.isBefore(scheduledEnd) || swapShiftEnd.isEqual(scheduledEnd))) {
                    accepterShiftIndex = i;
                    foundShiftStart = scheduledStart;
                    foundShiftEnd = scheduledEnd;
                    break;
                }
            }

            if (accepterShiftIndex == -1) {
                return "User is not scheduled during the shift they are offering to swap";
            }

            // Check for conflicts with dropper's existing schedule
            for (int i = 0; i < dropperScheduleList.size(); i += 2) {
                LocalDateTime scheduledStart = dropperScheduleList.get(i);
                LocalDateTime scheduledEnd = dropperScheduleList.get(i + 1);

                if (!(swapShiftEnd.isBefore(scheduledStart) || swapShiftEnd.isEqual(scheduledStart) || 
                     swapShiftStart.isAfter(scheduledEnd) || swapShiftStart.isEqual(scheduledEnd))) {
                    return "Swap shift conflicts with dropper's existing schedule";
                }
            }
        }

        // Check for conflicts with accepter's remaining schedule
        for (int i = 0; i < accepterScheduleList.size(); i += 2) {
            if (i == accepterShiftIndex) continue; // Skip the shift being swapped

            LocalDateTime scheduledStart = accepterScheduleList.get(i);
            LocalDateTime scheduledEnd = accepterScheduleList.get(i + 1);

            if (!(droppedShiftEnd.isBefore(scheduledStart) || droppedShiftEnd.isEqual(scheduledStart) || 
                 droppedShiftStart.isAfter(scheduledEnd) || droppedShiftStart.isEqual(scheduledEnd))) {
                return "Dropped shift conflicts with accepter's existing schedule";
            }
        }

        // Remove swap shift from accepter's schedule if provided
        if (swapShiftStart != null) {
            if (swapShiftStart.isEqual(foundShiftStart) && swapShiftEnd.isEqual(foundShiftEnd)) {
                // Full shift swap - remove the entire shift
                accepterScheduleList.remove(accepterShiftIndex + 1);
                accepterScheduleList.remove(accepterShiftIndex);
            } else {
                // Partial shift swap - remove only the swapped portion
                if (swapShiftStart.isEqual(foundShiftStart)) {
                    // Removing from start of shift
                    accepterScheduleList.set(accepterShiftIndex, swapShiftEnd);
                } else if (swapShiftEnd.isEqual(foundShiftEnd)) {
                    // Removing from end of shift
                    accepterScheduleList.set(accepterShiftIndex + 1, swapShiftStart);
                } else {
                    // Removing from middle - creates two shifts
                    accepterScheduleList.set(accepterShiftIndex + 1, swapShiftStart);
                    accepterScheduleList.add(accepterShiftIndex + 2, swapShiftEnd);
                    accepterScheduleList.add(accepterShiftIndex + 3, foundShiftEnd);
                }
            }
        }

        // Remove dropped shift from dropper's schedule
        for (int i = 0; i < dropperScheduleList.size(); i += 2) {
            LocalDateTime scheduledStart = dropperScheduleList.get(i);
            LocalDateTime scheduledEnd = dropperScheduleList.get(i + 1);
            
            if (scheduledStart.isEqual(droppedShiftStart) && scheduledEnd.isEqual(droppedShiftEnd)) {
                dropperScheduleList.remove(i + 1);
                dropperScheduleList.remove(i);
                break;
            }
        }

        // Add dropped shift to accepter
        accepterScheduleList.add(droppedShiftStart);
        accepterScheduleList.add(droppedShiftEnd);
        accepterScheduleList.sort(Comparator.naturalOrder());

        // Add swap shift to dropper if provided
        if (swapShiftStart != null) {
            dropperScheduleList.add(swapShiftStart);
            dropperScheduleList.add(swapShiftEnd);
            dropperScheduleList.sort(Comparator.naturalOrder());
        }

        // Save updated schedules
        accepterSchedule.setWork_schedule(accepterScheduleList);
        dropperSchedule.setWork_schedule(dropperScheduleList);
        userWorkScheduleRepository.save(accepterSchedule);
        userWorkScheduleRepository.save(dropperSchedule);

        // Remove the dropped shift from available shifts
        droppedShiftRepository.delete(droppedShift);

        if (swapShiftStart != null) {
            return "Shift swap successful: " + swapShiftStart + " to " + swapShiftEnd + " exchanged for " + droppedShiftStart + " to " + droppedShiftEnd;
        } else {
            return "Shift picked up successfully: " + droppedShiftStart + " to " + droppedShiftEnd;
        }
    }

    public String getSchedule(Long id){
        Optional<UserWorkSchedule> UserWorkSchedulOptional = userWorkScheduleRepository.findById(id);
        if (UserWorkSchedulOptional.isEmpty()){
            return "Schedule Not Found";
        }
        UserWorkSchedule userWorkSchedule = UserWorkSchedulOptional.get();

        return userWorkSchedule.toString();
    }
}
