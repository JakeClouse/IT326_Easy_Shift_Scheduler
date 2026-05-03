package com.EasyShiftScheduler.CalEnder.Helpers;

import java.util.List;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Entities.User;

public class CompensationReport {
    // Generates a compensation report string for a given user
    // Calculates total hours from clock-in/clock-out punch pairs, then multiplies by compensation rate
    public String generateReport(User user) {
        List<Punch> punches = user.getUser_timecard().getPunch_times();

        double totalHours = user.getUser_timecard().getWorked_hours();

        double totalPay = totalHours * user.getCompensation_rate();

        return "Employee: " + user.getUsername()
            + "\nHours Worked: " + String.format("%.2f", totalHours)
            + "\nCompensation Rate: $" + String.format("%.2f", user.getCompensation_rate())
            + "\nTotal Pay: $" + String.format("%.2f", totalPay);
    }
}
