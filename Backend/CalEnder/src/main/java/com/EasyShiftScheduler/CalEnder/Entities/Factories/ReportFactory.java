package com.EasyShiftScheduler.CalEnder.Entities.Factories;

import com.EasyShiftScheduler.CalEnder.Entities.CompensationReport;

public class ReportFactory {
    public Report generateReport(String reportBody){
        return (Report)(new CompensationReport(reportBody));
    }
}
