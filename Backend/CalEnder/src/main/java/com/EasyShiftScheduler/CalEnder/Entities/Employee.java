package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.Entity;

@Entity
public class Employee {
    
    // Private variables
    private double compensationRate;
    private AvailabilitySchedule[] availability;
    private EmployeeSchedule[] schedule;
    private Timecard timecard;
    
    // Constructor
    public Employee(double compensationRate, AvailabilitySchedule[] availability, EmployeeSchedule[] schedule, Timecard timecard) {
        this.compensationRate = compensationRate;
        this.availability = availability;
        this.schedule = schedule;
        this.timecard = timecard;
    }
    
    // Getter for compensation
    public double getCompensation() {
        // TODO: implement
        return 0;
    }
    
    // Setter for compensation
    public void setCompensation(double compensationRate) {
        // TODO: implement
    }
    
    // Setter for availability
    public void setAvailability(AvailabilitySchedule[] availability) {
        // TODO: implement
    }
    
    // Getter for availability
    public AvailabilitySchedule[] getAvailability() {
        // TODO: implement
        return null;
    }
    
    // Getter for timecard
    public Timecard getTimecard() {
        // TODO: implement
        return null;
    }
}
