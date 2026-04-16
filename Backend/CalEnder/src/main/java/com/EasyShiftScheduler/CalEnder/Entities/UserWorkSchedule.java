package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "USER_WORK_SCHEDULE")
public class UserWorkSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long work_schedule_id;

    @ElementCollection
    public List<LocalDateTime> work_schedule;

    public UserWorkSchedule() {
    }

    public UserWorkSchedule(Long work_schedule_id, List<LocalDateTime> work_schedule) {
        this.work_schedule_id = work_schedule_id;
        this.work_schedule = work_schedule;
    }

    public Long getWork_schedule_id() {
        return work_schedule_id;
    }

    public void setWork_schedule_id(Long work_schedule_id) {
        this.work_schedule_id = work_schedule_id;
    }

    public List<LocalDateTime> getWork_schedule() {
        return work_schedule;
    }

    public void setWork_schedule(List<LocalDateTime> work_schedule) {
        this.work_schedule = work_schedule;
    }
}
