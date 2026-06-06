package core.interfaces;

public interface ISceneManager {

    // App size constants.
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    /**
     * Switching to the Daily Log screen.
     */
    public void switchToDailyLog();

    /**
     * Switching to the Food Log screen.
     */
    public void switchToFoodLog();

    /**
     * Switching to the Exercise Log screen.
     */
    public void switchToExerciseLog();
}
