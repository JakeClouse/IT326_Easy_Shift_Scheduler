package com.EasyShiftScheduler.CalEnder.Services;

import com.EasyShiftScheduler.CalEnder.Repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(Long id, Group group) {
        return groupRepository.save(id, group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public Group getGroup(Long id) {
        groupRepository.getById(id);
    }
}
