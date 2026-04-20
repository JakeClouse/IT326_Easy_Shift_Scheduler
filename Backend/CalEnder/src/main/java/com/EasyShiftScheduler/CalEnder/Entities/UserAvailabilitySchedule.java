package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_AVAILABILITY_SCHEDULE")
public class UserAvailabilitySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long availability_schedule_id;

    @ElementCollection
    public List<LocalDateTime> availability_schedule;

    public UserAvailabilitySchedule() {
    }

    public UserAvailabilitySchedule(Long availability_schedule_id, List<LocalDateTime> availability_schedule) {
        this.availability_schedule_id = availability_schedule_id;
        this.availability_schedule = availability_schedule;
    }

    public Long getAvailability_schedule_id() {
        return availability_schedule_id;
    }

    public void setAvailability_schedule_id(Long availability_schedule_id) {
        this.availability_schedule_id = availability_schedule_id;
    }

    public List<LocalDateTime> getAvailability_schedule() {
        return availability_schedule;
    }

    public void setAvailability_schedule(List<LocalDateTime> availability_schedule) {
        this.availability_schedule = availability_schedule;
    }
}
