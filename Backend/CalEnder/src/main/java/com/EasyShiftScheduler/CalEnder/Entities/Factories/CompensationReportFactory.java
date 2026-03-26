package com.EasyShiftScheduler.CalEnder.Entities.Factories;

import com.EasyShiftScheduler.CalEnder.Entities.CompensationReport;

public class CompensationReportFactory {
    public CompensationReport generateReport(String reportBody){
        return new CompensationReport(reportBody);
    }
}
