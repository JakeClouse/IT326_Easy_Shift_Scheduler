package com.EasyShiftScheduler.CalEnder.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.DroppedShift;
import com.EasyShiftScheduler.CalEnder.Services.DroppedShiftService;

@RestController
@RequestMapping("/api/dropped-shift")
public class DroppedShiftController {
    private DroppedShiftService droppedShiftService;

    public DroppedShiftController(DroppedShiftService droppedShiftService) {
        this.droppedShiftService = droppedShiftService;
    }

    @PostMapping("/{id}/drop-shift")
    public String dropShift(@PathVariable("id") long userID, @RequestBody DroppedShift shiftToDrop) {
        return droppedShiftService.dropShift(userID, shiftToDrop);
    }

    @GetMapping("/view-dropped-shifts")
    public String getDroppedShifts() {
        return droppedShiftService.getDroppedShifts();
    }
}
