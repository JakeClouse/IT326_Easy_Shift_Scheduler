package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Employee {
    
    // Private variables
    private double compensationRate;
    private List<AvailabilitySchedule> availability;
    private List<EmployeeSchedule> schedule;
    private Timecard timecard;
    private long id;

    public double getCompensationRate() {
        return compensationRate;
    }

    public void setCompensationRate(double compensationRate) {
        this.compensationRate = compensationRate;
    }

    public void setAvailability(List<AvailabilitySchedule> availability) {
        this.availability = availability;
    }

    public List<EmployeeSchedule> getSchedule() {
        return schedule;
    }

    public void setTimecard(Timecard timecard) {
        this.timecard = timecard;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Constructor
    public Employee(double compensationRate, List<AvailabilitySchedule> availability, List<EmployeeSchedule> schedule, Timecard timecard) {
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

    public void setSchedule(List<EmployeeSchedule> schedule) {
        this.schedule = schedule;
    }
}
