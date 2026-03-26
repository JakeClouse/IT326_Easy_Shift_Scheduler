package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.Entity;

@Entity
public class TimeOffRequest {
    
    // Private variables
    private String startDate;
    private String endDate;
    private String reason;
    private boolean approved;
    private Employee employee;
    private Employer employer;
    
    // Constructor
    public TimeOffRequest(String startDate, String endDate, String reason, boolean approved, Employee employee, Employer employer) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.approved = approved;
        this.employee = employee;
        this.employer = employer;
    }
    
    // Getter for startDate
    public String getStartDate() {
        // TODO: implement
        return null;
    }
    
    // Getter for endDate
    public String getEndDate() {
        // TODO: implement
        return null;
    }
    
    // Getter for reason
    public String getReason() {
        // TODO: implement
        return null;
    }
    
    // Getter for approved
    public boolean getApproved() {
        // TODO: implement
        return false;
    }
    
    // Setter for startDate
    public void setStartDate(String startDate) {
        // TODO: implement
    }
    
    // Setter for endDate
    public void setEndDate(String endDate) {
        // TODO: implement
    }
    
    // Setter for reason
    public void setReason(String reason) {
        // TODO: implement
    }
    
    // Setter for approved
    public void setApproved(Boolean isApproved) {
        // TODO: implement
    }
}
