package cz.muni.fi.pv260.checker;

public class VisitReport {
    private boolean passed;

    public boolean hasPassed() {
        return passed;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    private String name;
    private String message;

    public VisitReport(boolean passed, String name, String message){
        this.passed = passed;
        this.name = name;
        this.message = message;
    }
}
