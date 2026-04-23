package com.EasyShiftScheduler.CalEnder.Helpers;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupOperations {

    public Group removeMemberFromGroup(Group group, User userToRemove) {
        List<User> users = group.getUsers();
        users.remove(userToRemove);
        group.setUsers(users);
        return group;
    }


}
