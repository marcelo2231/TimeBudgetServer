package timebudget.model.response;

import java.util.Map;

public class ReportResponse {
    private Map<Integer, Float> report;

    public ReportResponse(Map<Integer,Float> report){
        this.report = report;
    }

    public Map<Integer, Float> getReport() {
        return report;
    }

    public void setReport(Map<Integer, Float> report) {
        this.report = report;
    }

}