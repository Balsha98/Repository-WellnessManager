package core.models.entries;

import java.time.LocalDate;
import core.interfaces.IEntryType;

public class FoodEntry implements IEntryType {

    // Attributes.
    private LocalDate date;
    public static final String TYPE = "Food Consumed";
    private String foodName;
    private double servingsCount;

    /**
     * Parameterized constructor.
     * 
     * @param date          - date added.
     * @param foodName      - food name.
     * @param servingsCount - # of servings.
     */
    public FoodEntry(LocalDate date, String foodName, double servingsCount) {
        this.date = date;
        this.foodName = foodName;
        this.servingsCount = servingsCount;
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
    public String getFoodName() {
        return foodName;
    }

    // Inherited method.
    public double getAmount() {
        return servingsCount;
    }

    // Inherited method.
    public String toString() {
        String[] dateValues = dateFormatter.format(date).split("-");
        return String.format("%s,%s,%s,f,%s,%.1f%n",
                dateValues[0],
                dateValues[1],
                dateValues[2],
                this.getFoodName(),
                this.getAmount());
    }
}
