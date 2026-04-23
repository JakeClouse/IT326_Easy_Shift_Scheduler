package com.EasyShiftScheduler.CalEnder.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Entities.User;
import com.EasyShiftScheduler.CalEnder.Helpers.GroupOperations;
import com.EasyShiftScheduler.CalEnder.Repositories.GroupRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.UserRepository;

@Service
public class GroupService {
    private GroupOperations groupOperations;
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    public GroupService(){

    }

    public String removeEmployee(long groupID, long employeeID, long employerID) {
        Optional<User> employee = userRepository.findById(employeeID);
        Optional<User> employer = userRepository.findById(employerID);
        Optional<Group> group = groupRepository.findById(groupID);

        if (employee.isEmpty()){
            return "Error: employee not found";
        }

        if (employer.isEmpty()) {
            return "Error: employer not found";
        }

        if (group.isEmpty()) {
            return "Error: group not found";
        }

        List<User> userGroup = group.get().getGroup_users();

        if (userGroup.contains(employee.get()) && userGroup.contains(employer.get())){
            userGroup.remove(employee.get());

            // Update group's members
            group.get().setGroup_users(userGroup);

            // Update employee's groups
            List<Group> employeesGroup = employee.get().getGroups();
            employeesGroup.remove(group.get());
            employee.get().setGroups(employeesGroup);

        }
        else {
            return "Error: employee and employer not in same group";
        }

        // Save Group and employee back to DB
        groupRepository.save(group.get());
        userRepository.save(employee.get());

        return "Successfully remove employee from group";
    }

    public Group createGroup(List<Long> userIds){
        Group group = new Group();
        
        for(int i = 0; i < userIds.size(); i++){
             Optional<User> user = userRepository.findById(userIds.get(i));

            if (!user.isEmpty()){
                group.getGroup_users().add(user.get());
            }
        }

        Group newGroup = groupRepository.save(group);

        //cleanpup so users have reference as well.
        for(int i = 0; i < userIds.size(); i++){
            Optional<User> user = userRepository.findById(userIds.get(i));

            if (!user.isEmpty()){
                user.get().getGroups().add(newGroup);
                userRepository.save(user.get());
            }
        }
        return newGroup;
    }





}
