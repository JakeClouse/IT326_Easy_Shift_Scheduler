package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.Entity;

@Entity
public class Group {
    
    GroupOperations groupOperations;

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

}
