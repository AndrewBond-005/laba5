package models.ask;

public class AskBreak extends Exception {
    private final String report;

    public AskBreak(String report) {
        this.report = report;
    }

    public String getReport() {
        return report;
    }
}