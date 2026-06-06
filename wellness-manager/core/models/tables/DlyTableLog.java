package core.models.tables;

import java.time.LocalDate;
import javafx.beans.property.*;

// Class used for the Daily Log Table.
public class DlyTableLog {

    // Column values.
    private SimpleObjectProperty<LocalDate> logDate;
    private SimpleStringProperty logType, logName;
    private SimpleDoubleProperty logAmount;

    // Parameterized constructor.
    public DlyTableLog(LocalDate logDate, String logType, String logName, double logAmount) {
        this.logDate = new SimpleObjectProperty<>(logDate);
        this.logType = new SimpleStringProperty(logType);
        this.logName = new SimpleStringProperty(logName);
        this.logAmount = new SimpleDoubleProperty(logAmount);
    }

    // Getting the entry date.
    public SimpleObjectProperty<LocalDate> getDate() {
        return this.logDate;
    }

    // Getting the entry type.
    public SimpleStringProperty getType() {
        return this.logType;
    }

    // Getting the entry name.
    public SimpleStringProperty getName() {
        return this.logName;
    }

    // Getting the entry amount.
    public SimpleDoubleProperty getAmount() {
        return this.logAmount;
    }
}
