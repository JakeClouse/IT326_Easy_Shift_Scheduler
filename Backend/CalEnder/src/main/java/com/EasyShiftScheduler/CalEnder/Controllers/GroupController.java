package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Services.GroupService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group/")
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
}
