package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "USER_TIMECARD")
public class UserTimecard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long timecard_id;

    @OneToMany(mappedBy = "user_timecard")
    private List<Punch> punch_times;

    public UserTimecard(){}

    public UserTimecard(Long timecard_id, List<Punch> punch_times) {
        this.timecard_id = timecard_id;
        this.punch_times = punch_times;
    }
}