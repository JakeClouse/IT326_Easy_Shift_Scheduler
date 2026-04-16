package com.EasyShiftScheduler.CalEnder.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Services.PunchService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/punch")
public class PunchController {

    @Autowired
    private PunchService punchService;

    @PostMapping
    public Punch CreatePunch(@RequestBody Punch punch) {
        return punchService.CreatePunch(punch);
    }

    @GetMapping("/{id}")
    public Punch ReadPunch(@PathVariable long id) {
        return punchService.ReadPunch(id);
    }

    @PostMapping("/{id}")
    public Punch UpdatePunch(@RequestBody Punch punch, @PathVariable long id) {
        return punchService.UpdatePunch(punch, id);
    }

    @DeleteMapping("/{id}")
    public void DeletePunch(@PathVariable long id) {
        punchService.DeletePunch(id);
    }
    
    //Use Case 25: Assign Punch Reason
    @PutMapping("/{id}/reason")
    public Punch assignReason(@PathVariable long id, @RequestBody String reason) {
        return punchService.assignReason(id, reason);
    }
}

