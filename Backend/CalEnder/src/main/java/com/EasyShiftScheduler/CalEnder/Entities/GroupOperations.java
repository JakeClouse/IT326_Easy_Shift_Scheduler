package com.EasyShiftScheduler.CalEnder.Entities;

import java.util.ArrayList;

public class GroupOperations {
    
    // Getter for members
    public ArrayList<User> getMembers() {
        // TODO: implement
        return null;
    }
    
    // Remove a member from the group
    public void removeMember(User userToRemove) {
        ArrayList<User> members = getMembers();
        members.remove(userToRemove);
    }
    
    // Add a member to the group
    public void addMember(User userToAdd) {
        // TODO: implement
    }
    
    // Getter for schedule
    public Object getSchedule() {
        // TODO: implement
        return null;
    }
}
