package core.models.entries;

import java.time.LocalDate;
import core.interfaces.IEntryType;

public class CalorieLimitEntry implements IEntryType {

    // Attributes.
    private LocalDate date;
    public static final String TYPE = "Calorie Limit";
    private double calorieLimit;

    /**
     * Parameterized constructor.
     * 
     * @param date         - date added.
     * @param calorieLimit - the calorie limit.
     */
    public CalorieLimitEntry(LocalDate date, double calorieLimit) {
        this.date = date;
        this.calorieLimit = calorieLimit;
    }

    // Inherited method.
    public LocalDate getDate() {
        return date;
    }

    // Inherited method.
    public String getType() {
        return TYPE;
    }

    // Inherited method.
    public double getAmount() {
        return calorieLimit;
    }

    // Inherited method.
    public String toString() {
        String[] dateValues = dateFormatter.format(date).split("-");
        return String.format("%s,%s,%s,c,%.1f%n",
                dateValues[0],
                dateValues[1],
                dateValues[2],
                this.getAmount());
    }
}
