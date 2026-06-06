package core.interfaces;

public interface IExerciseType {

    /**
     * Getting the exercise name.
     * 
     * @return - the name.
     */
    public String getName();

    /**
     * Getting the exercise's calories.
     * 
     * @return - the calories.
     */
    public double getCalories();

    /**
     * Getting the formatted String.
     * 
     * @return - formatted String.
     */
    public String toString();
}
