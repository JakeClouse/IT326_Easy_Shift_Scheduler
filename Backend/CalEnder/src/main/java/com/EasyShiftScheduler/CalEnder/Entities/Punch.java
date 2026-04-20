package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PUNCH")
public class Punch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long punch_id;

    @Column
    public LocalDateTime punch_time;

    @Column
    public String reason;

    @ManyToOne
    @JoinColumn(name="timecard_id")
    private UserTimecard user_timecard;

    public Punch() {
    }

    public Punch(Long punch_id, LocalDateTime punch_time, String reason, UserTimecard user_timecard) {
        this.punch_id = punch_id;
        this.punch_time = punch_time;
        this.reason = reason;
        this.user_timecard = user_timecard;
    }

    public Long getPunch_id() {
        return punch_id;
    }

    public void setPunch_id(Long punch_id) {
        this.punch_id = punch_id;
    }

    public LocalDateTime getPunch_time() {
        return punch_time;
    }

    public void setPunch_time(LocalDateTime punch_time) {
        this.punch_time = punch_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public UserTimecard getUser_timecard() {
        return user_timecard;
    }

    public void setUser_timecard(UserTimecard user_timecard) {
        this.user_timecard = user_timecard;
    }
}


