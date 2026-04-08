package com.EasyShiftScheduler.CalEnder.Entities;

import java.time.LocalDateTime;

public class Punch {
    public LocalDateTime time;
    public String reason;

    public Punch(LocalDateTime time, String reason){
        this.time = time;
        this.reason = reason;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
