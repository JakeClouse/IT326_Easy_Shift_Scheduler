package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.DroppedShift;
import com.EasyShiftScheduler.CalEnder.Entities.TimeOffRequest;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Entities.UserAvailabilitySchedule;
import com.EasyShiftScheduler.CalEnder.Entities.UserWorkSchedule;
import com.EasyShiftScheduler.CalEnder.Repositories.TimeOffRequestRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TimeOffRequestService {
    private TimeOffRequestRepository TimeOffRequestRepository;
    private UserRepository userRepository;

    public TimeOffRequestService(TimeOffRequestRepository TimeOffRequestRepository, UserRepository userRepository){
        this.TimeOffRequestRepository = TimeOffRequestRepository;
        this.userRepository = userRepository;
    }

    public TimeOffRequest assignReason(Long id, String reason) {
        TimeOffRequest TimeOffRequest = TimeOffRequestRepository.findById(id).orElse(null);
        if (TimeOffRequest != null) {
            TimeOffRequest.setReason(reason);
            return TimeOffRequestRepository.save(TimeOffRequest);
        }
        return null;
    }

    public String requestTimeOff(long userID, TimeOffRequest shiftToDrop) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            shiftToDrop.setUser_that_requested(user.get());
            List<TimeOffRequest> timeOffRequest = user.get().getTime_off_requests();
            timeOffRequest.add(shiftToDrop);
            user.get().setTime_off_requests(timeOffRequest);
            userRepository.save(user.get());
            return "Time off request submitted successfully";
        }
        else {
            return "User not found";
        }
    }

    public String getTimeOffRequests() {
        List<TimeOffRequest> timeOffRequests = TimeOffRequestRepository.findAll();
        return timeOffRequests.toString();
    }

    public String approveTimeOffRequest(long requestID) {
        Optional<TimeOffRequest> timeOffRequest = TimeOffRequestRepository.findById(requestID);
        if (timeOffRequest.isPresent()) {
            TimeOffRequest request = timeOffRequest.get();
            request.setApproved(true);
            TimeOffRequestRepository.save(request);
            Optional<User> user = userRepository.findById(request.getUser_that_requested().getId());
            if (user.isPresent()) {
                User actualUser = user.get();
                UserWorkSchedule userSchedule = actualUser.getWork_schedule();
                UserAvailabilitySchedule userAvailSchedule = actualUser.getAvailability_schedule();
                List<LocalDateTime> userScheduleList = userSchedule.getWork_schedule();
                List<LocalDateTime> userAvailScheduleList = userAvailSchedule.getAvailability_schedule();
                LocalDateTime start = request.getStartDate();
                LocalDateTime end = request.getEndDate();
                userScheduleList.removeIf(date -> (date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end)));
                userSchedule.setWork_schedule(userScheduleList);
                actualUser.setWork_schedule(userSchedule);
                userAvailScheduleList.removeIf(date -> (date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end)));
                userAvailSchedule.setAvailability_schedule(userAvailScheduleList);
                actualUser.setAvailability_schedule(userAvailSchedule);
                userRepository.save(actualUser);
            }
            return "Time off request approved";
        } else {
            return "Time off request not found";
        }
    }

    public String denyTimeOffRequest(long requestID) {
        Optional<TimeOffRequest> timeOffRequest = TimeOffRequestRepository.findById(requestID);
        if (timeOffRequest.isPresent()) {
            TimeOffRequest request = timeOffRequest.get();
            request.setApproved(false);
            TimeOffRequestRepository.save(request);
            return "Time off request denied";
        } else {
            return "Time off request not found";
        }
    }
}
