package com.EasyShiftScheduler.CalEnder.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Services.GroupService;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // Remove Employee from Group
    @DeleteMapping("/remove-user-from-group")
    public String removeEmployeeFromGroup(@RequestParam("groupID") long groupID, @RequestParam("employeeID") long employeeID, @RequestParam("employerID") long employerID) {
        return groupService.removeEmployee(groupID, employeeID, employerID);
    }

    @PostMapping("/create-group")
    public ResponseEntity<Group> createGroup(@RequestParam("userIDs") List<Long> userIDs){
        Group g = groupService.createGroup(userIDs);
        return ResponseEntity.ok(g);
    }
}
