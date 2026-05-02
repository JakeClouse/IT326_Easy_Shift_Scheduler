package com.EasyShiftScheduler.CalEnder.Helpers;

import com.EasyShiftScheduler.CalEnder.Entities.Punch;
import com.EasyShiftScheduler.CalEnder.Entities.User;

import java.time.Duration;
import java.util.List;

public class CompensationReport {
    // Generates a compensation report string for a given user
    // Calculates total hours from clock-in/clock-out punch pairs, then multiplies by compensation rate
    public String generateReport(User user) {
        List<Punch> punches = user.getUser_timecard().getPunch_times();

        double totalHours = 0;
        if (punches != null) {
            // Punches are expected in clock-in / clock-out pairs
            for (int i = 0; i + 1 < punches.size(); i += 2) {
                Duration duration = Duration.between(punches.get(i).getPunch_time(), punches.get(i + 1).getPunch_time());
                totalHours += duration.toMinutes() / 60.0;
            }
        }

        double totalPay = totalHours * user.getCompensation_rate();

        return "Employee: " + user.getUsername()
            + "\nHours Worked: " + String.format("%.2f", totalHours)
            + "\nCompensation Rate: $" + String.format("%.2f", user.getCompensation_rate())
            + "\nTotal Pay: $" + String.format("%.2f", totalPay);
    }
}
