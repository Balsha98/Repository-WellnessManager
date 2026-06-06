package core.models.types;

import core.interfaces.IExerciseType;

public class ExerciseType implements IExerciseType {

    // Attributes.
    private static final String INITIAL = "e";
    private String name;
    private double calories;

    /**
     * Parameterized constructor.
     * 
     * @param name     - exercise name.
     * @param calories - exercise calories.
     */
    public ExerciseType(String name, double calories) {
        this.name = name;
        this.calories = calories;
    }

    // Inherited method.
    public String getName() {
        return name;
    }

    // Inherited method.
    public double getCalories() {
        return calories;
    }

    // Inherited method.
    public String toString() {
        return String.format(
                "%s,%s,%.1f%n",
                INITIAL,
                this.name,
                this.calories);
    }
}
