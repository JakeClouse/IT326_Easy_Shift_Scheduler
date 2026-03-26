package com.EasyShiftScheduler.CalEnder.Controllers;

import com.EasyShiftScheduler.CalEnder.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.saveUser(user);
    }

    @PostMapping
    public Group updateGroup(@RequestBody Group group) {
        return groupService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        groupService.getById(id);
    }

}
