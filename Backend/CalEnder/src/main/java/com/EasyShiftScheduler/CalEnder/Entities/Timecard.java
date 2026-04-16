package com.EasyShiftScheduler.CalEnder.Entities;

public class Timecard{
    private Punch[] punchTimes;

    private double hours;

    public Timecard(Punch[] punchArray){
        punchTimes = punchArray;
    }

    public void setPunchTimes(Punch[] punchArray){
        punchTimes = punchArray;
    }

    public void addPunches(Punch[] newPunches){
        punchTimes = newPunches;
    }

    public void overrideHours(double newHours) { hours = newHours; }

    public Punch[] getTimecard(){
        return punchTimes;
    }
}