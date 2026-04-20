package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Services.PunchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class PunchController {
    private PunchService punchService;

    public PunchController(PunchService punchService){
        this.punchService = punchService;
    }

    //Use Case 25: Assign Punch Reason
    @PutMapping("/{id}/reason")
    public Punch assignReason(@PathVariable long id, @RequestBody String reason) {
        return punchService.assignReason(id, reason);
    }
}
