package com.EasyShiftScheduler.CalEnder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EasyShiftScheduler.CalEnder.Entities.Group;
import com.EasyShiftScheduler.CalEnder.Repositories.EmployeeRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.EmployerRepository;
import com.EasyShiftScheduler.CalEnder.Repositories.GroupRepository;


@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(Long id, Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public Group getGroup(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public void removeMemberFromGroup(Long groupId, Long memberId) {
        Group group = getGroup(groupId);
        if (group != null) {
            if (employerRepository.findById(memberId).isPresent()) {
                group.groupOperations.removeMember(employerRepository.findById(memberId).orElse(null));
            }
            else if (employeeRepository.findById(memberId).isPresent()) {          
                group.groupOperations.removeMember(employeeRepository.findById(memberId).orElse(null));
            }
            updateGroup(groupId, group);
        }
    }

    
}
