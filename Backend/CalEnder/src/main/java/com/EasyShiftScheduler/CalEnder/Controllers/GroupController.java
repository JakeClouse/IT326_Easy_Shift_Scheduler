package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Services.EmployeeService;
import com.EasyShiftScheduler.CalEnder.Services.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @PostMapping
    public Group updateGroup(@RequestBody Group group) {
        return groupService.saveGroup(group.getId(), group);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        return groupService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void removeEmployeeFromGroup(@PathVariable Long id, @RequestParam Long employeeId) {
        groupService.removeMemberFromGroup(id, employeeId);
    }

}
