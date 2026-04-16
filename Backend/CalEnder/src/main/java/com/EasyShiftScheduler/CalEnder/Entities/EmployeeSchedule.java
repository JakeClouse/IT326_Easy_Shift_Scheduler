package com.EasyShiftScheduler.CalEnder.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class EmployeeSchedule {
    public ArrayList<LocalDateTime[]> schedule;

    public void setSchedule(ArrayList<LocalDateTime[]> newSchedule) {
        this.schedule = newSchedule;
    }

    public ArrayList<LocalDateTime[]> getSchedule() {
        return this.schedule;
    }

    public void removeShift(LocalDateTime startTime) {
        for(int i = 0; i < schedule.size(); i++) {
            if(schedule.get(i)[0].isEqual(startTime)) {
                schedule.remove(i);
                break;
            }
        }
    }
}
