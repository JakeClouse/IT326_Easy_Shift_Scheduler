package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Services.PunchService;


@RestController
@RequestMapping("/api/user")
public class PunchController {
    private PunchService punchService;

    public PunchController(PunchService punchService){
        this.punchService = punchService;
    }

    //Use Case 25: Assign Punch Reason
    @PutMapping("/reason")
    public Punch assignReason(@RequestParam long id, @RequestBody String reason) {
        return punchService.assignReason(id, reason);
    }

    @GetMapping("/getPunch")
    public String getPunch(@RequestParam long punchId) {
        return punchService.getPunch(punchId);
    }
    
}
