package com.EasyShiftScheduler.CalEnder.Entities.Factories;

public class CompensationReport implements Report {
    private String rawReportData;

    public CompensationReport(String rawReportData) {
        this.rawReportData = rawReportData;
    }

    @Override
    public String getRawReportData() {
        return this.rawReportData;
    }
}
