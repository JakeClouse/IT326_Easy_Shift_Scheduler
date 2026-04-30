package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Services.UserTimeCardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user_timecard")
@AllArgsConstructor
public class UserTimeCardController {
    private UserTimeCardService userTimeCardService;

    @PutMapping("/{userID}/worked_hours/{worked_hours}")
    public String updateWorkedHours(@PathVariable long userID, @PathVariable int worked_hours){
        return userTimeCardService.setWorkedHours(userID, worked_hours);
    }
}
