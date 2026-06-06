package core.models.logs;

import java.util.ArrayList;
import java.util.List;
import core.interfaces.IExerciseType;
import core.models.Model;

public class ExerciseLog {

    // Singleton instance.
    private static ExerciseLog instance;

    // Attributes.
    private static final double WEIGHT = 160;
    public List<IExerciseType> logEntries;

    // Default constructor.
    private ExerciseLog() {
        logEntries = new ArrayList<>();
    }

    // Singleton instantiation.
    public static ExerciseLog getInstance() {
        if (instance == null) {
            instance = new ExerciseLog();
        }

        return instance;
    }

    /**
     * Making sure exercises are unique.
     * 
     * @param newExercise - exercise to be added.
     * @return - true/false.
     */
    public boolean doesExerciseExist(IExerciseType newExercise) {
        if (this.logEntries.isEmpty())
            return false;

        for (IExerciseType logEntry : this.logEntries)
            if (logEntry.getName().equals(newExercise.getName()))
                return true;

        return false;
    }

    /**
     * Calculating the amount of calories burnt.
     * 
     * @param exName     - exercise name.
     * @param exDuration - exercise duration.
     * @return - amount of calories burnt.
     */
    public double getCaloriesBurnt(String exName, double exDuration) {
        double caloriesBurnt = 0;

        List<String> exList = Model.getInstance().getData("exercise");
        for (String exLog : exList) {
            String[] eLogParts = exLog.split(",");
            double exCalories = Double.parseDouble(eLogParts[2]);

            if (eLogParts[1].equals(exName)) {
                caloriesBurnt = exCalories * (WEIGHT / 100) * (exDuration / 60);
            }
        }

        return caloriesBurnt;
    }

    /**
     * Adding a new exercise.
     * 
     * @param newExercise - exercise to be added.
     */
    public void addExerciseLog(IExerciseType newExercise) {
        this.logEntries.add(newExercise);
    }

    /**
     * Getting the list of log entries.
     * 
     * @return - list of log entries.
     */
    public List<IExerciseType> getLogEntries() {
        Model.getInstance().loadELogsFromDb();
        return logEntries;
    }
}
