package com.EasyShiftScheduler.CalEnder.Entities;

import com.EasyShiftScheduler.CalEnder.Entities.Abstract.User;

import jakarta.persistence.Entity;

@Entity
public class Group {
    
    // Private variables
    private User[] groupMembers;
    
    // Constructor
    public Group(User[] groupMembers) {
        this.groupMembers = groupMembers;
    }
    
    // Getter for groupMembers
    public User[] getGroupMembers() {
        // TODO: implement
        return null;
    }
    
    // Setter for groupMembers
    public void setGroupMembers(User[] groupMembers) {
        // TODO: implement
    }
    
    // Getter for members
    public User[] getMembers() {
        // TODO: implement
        return null;
    }
    
    // Remove a member from the group
    public void removeMember(User userToRemove) {
        // TODO: implement
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
