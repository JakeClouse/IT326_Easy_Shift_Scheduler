package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Entities.DroppedShift;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Repositories.DroppedShiftsRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class DroppedShiftService {
    private UserRepository userRepository;
    private DroppedShiftsRepository droppedShiftsRepository;

    public DroppedShiftService(DroppedShiftsRepository droppedShiftsRepository, UserRepository userRepository){
        this.droppedShiftsRepository = droppedShiftsRepository;
        this.userRepository = userRepository;
    }

    public String dropShift(long userID, DroppedShift shiftToDrop) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()){
            shiftToDrop.setUser_that_requested(user.get());
            List<DroppedShift> shifts = user.get().getDropped_shifts();
            shifts.add(shiftToDrop);
            user.get().setDropped_shifts(shifts);
            userRepository.save(user.get());
            return "Shift dropped successfully";
        }
        else {
            return "User not found";
        }
    }

    public String getDroppedShifts() {
        List<DroppedShift> droppedShifts = droppedShiftsRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (DroppedShift shift : droppedShifts) {
            sb.append("Shift ID: ").append(shift.getId()).append(", User ID: ").append(shift.getUser_that_requested().getId()).append(", InTime: ").append(shift.getStartDate()).append(", OutTime: ").append(shift.getEndDate()).append(", Reason: ").append(shift.getReason()).append("\n");
        }
        return sb.toString();
    }

    

}
