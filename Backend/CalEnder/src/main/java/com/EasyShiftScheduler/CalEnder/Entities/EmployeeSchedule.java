package com.EasyShiftScheduler.CalEnder.Entities;

import java.util.Calendar;

public class EmployeeSchedule {
    public Calendar schedule;

    public void setSchedule(Calendar newSchedule) {
        this.schedule = newSchedule;
    }

    public Calendar getSchedule() {
        return this.schedule;
    }
}
