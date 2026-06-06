package core.models.entries;

import java.time.LocalDate;
import core.interfaces.IEntryType;

public class ExerciseEntry implements IEntryType {

    private LocalDate date;
    public static final String TYPE = "Exercise Completed";
    private String exerciseName;
    private double minutesPassed;

    public ExerciseEntry(LocalDate date, String exerciseName, double minutesPassed) {
        this.date = date;
        this.exerciseName = exerciseName;
        this.minutesPassed = minutesPassed;
    }

    // Inherited method.
    public LocalDate getDate() {
        return date;
    }

    // Inherited method.
    public String getType() {
        return TYPE;
    }

    /**
     * Getting the exercise's name;
     * 
     * @return - exercise name.
     */
    public String getExerciseName() {
        return exerciseName;
    }

    // Inherited method.
    public double getAmount() {
        return minutesPassed;
    }

    // Inherited method.
    public String toString() {
        String[] dateValues = dateFormatter.format(date).split("-");
        return String.format("%s,%s,%s,e,%s,%.1f%n",
                dateValues[0],
                dateValues[1],
                dateValues[2],
                this.getExerciseName(),
                this.getAmount());
    }
}
