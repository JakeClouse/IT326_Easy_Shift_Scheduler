package com.EasyShiftScheduler.CalEnder.Entities;

public class Timecard{
    private Punch[] punchTimes;

    public Timecard(Punch[] punchArray){
        punchTimes = punchArray;
    }

    public void setPunchTimes(Punch[] punchArray){
        punchTimes = punchArray;
    }

    public void addPunches(Punch[] newPunches){
        punchTimes = newPunches;
    }

    public Punch[] getTimecard(){
        return punchTimes
    }
}