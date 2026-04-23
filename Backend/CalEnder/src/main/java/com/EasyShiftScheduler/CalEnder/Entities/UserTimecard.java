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

    public Long getTimecard_id() {
        return timecard_id;
    }

    public void setTimecard_id(Long timecard_id) {
        this.timecard_id = timecard_id;
    }

    public List<Punch> getPunch_times() {
        return punch_times;
    }

    public void setPunch_times(List<Punch> punch_times) {
        this.punch_times = punch_times;
    }
}