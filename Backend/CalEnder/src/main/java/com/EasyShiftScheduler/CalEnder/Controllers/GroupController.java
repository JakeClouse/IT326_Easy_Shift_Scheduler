package com.EasyShiftScheduler.CalEnder.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Services.GroupService;
import com.EasyShiftScheduler.CalEnder.Services.UserService;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    private GroupService groupService;
    private UserService userService;

    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    // Remove Employee from Group
    @DeleteMapping("/remove-user-from-group")
    public String removeEmployeeFromGroup(@RequestParam("groupID") long groupID, @RequestParam("employeeID") long employeeID, @RequestParam("employerID") long employerID) {
        return groupService.removeEmployee(groupID, employeeID, employerID);
    }

    @PostMapping("/create-group")
    public String createGroup(@RequestParam("userIDs") List<Long> userIDs){
        Group g = groupService.createGroup(userIDs);
        return g.toString();
    }

    @GetMapping("/publishSchedule")
    public String publishSchedule(@RequestParam("groupID") Long groupID) {
        return groupService.publishSchedule(groupID);
    }

    @GetMapping("/generateReport")
    public String generateReport(@RequestParam("groupID") Long groupID) {
        return groupService.generateReport(groupID);
    }
    
    @PutMapping("/groups/join")
    public String joinGroup(@RequestParam long userID, @RequestParam long groupID) {
        return userService.joinGroup(userID, groupID);
    }

    @GetMapping("/getGroup")
    public String getGroup(@RequestParam("groupID") Long groupID) {
        return groupService.getGroup(groupID);
    }

    @GetMapping("/getGroups")
    public String getGroups() {
        return groupService.getGroups();
    }

}

