import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Model {

    // Singleton instance.
    private static Model instance;

    // Attributes.
    private static final String DB_DIR = "./assets/docs/%s.csv";
    private String[] dbNames = { "users", "foods", "exercise", "log" };
    private Map<String, List<String>> dataMap;
    private DailyLog dailyLog;
    private FoodLog foodLog;
    private ExerciseLog exerciseLog;

    /**
     * Parameterized constructor.
     * 
     * @param dbName - name of database.
     */
    private Model() {
        dataMap = new TreeMap<>();
        for (String dbName : dbNames) {
            dataMap.put(dbName, new ArrayList<>());
        }

        dailyLog = DailyLog.getInstance();
        foodLog = FoodLog.getInstance();
        exerciseLog = ExerciseLog.getInstance();
    }

    // Singleton instantiation.
    public static Model getInstance() {
        if (instance == null)
            instance = new Model();

        return instance;
    }

    /**
     * Getting the data list.
     * 
     * @return - data list.
     */
    public List<String> getData(String mapKey) {
        readData(mapKey);
        return dataMap.get(mapKey);
    }

    /**
     * Reading and/or showing the data.
     * 
     * @param showData - read/show.
     */
    private void readData(String mapKey) {
        String filePath = String.format(DB_DIR, mapKey);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            if (!dataMap.get(mapKey).isEmpty())
                dataMap.get(mapKey).clear();

            String fileData = reader.readLine();
            while (fileData != null) {
                dataMap.get(mapKey).add(fileData);
                fileData = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------- HOME_VBOX DATA MANIPULATION ---------- //
    /**
     * Adding a new user to the database
     * 
     * @param newUser - a new user.
     */
    public void addNewUserToDb(User newUser) {
        String filePath = String.format(DB_DIR, dbNames[0]);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(newUser.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------- DAILY_LOG_VBOX DATA MANIPULATION ---------- //
    public void loadDLogsFromDb() {
        if (!dailyLog.logEntries.isEmpty())
            dailyLog.logEntries.clear();

        List<String> dbList = this.getData("log");
        for (int i = 0; i < dbList.size(); i++) {
            String[] parts = dbList.get(i).split(",");
            if (parts.length >= 5) {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                LocalDate date = LocalDate.of(year, month, day);

                String type = parts[3];
                if (type.equals("f")) {
                    String foodName = parts[4];
                    double servingsCount = Double.parseDouble(parts[5]);
                    dailyLog.addLogEntry(new FoodEntry(date, foodName, servingsCount));
                } else if (type.equals("w")) {
                    double weight = Double.parseDouble(parts[4]);
                    dailyLog.addLogEntry(new WeightEntry(date, weight));
                } else if (type.equals("c")) {
                    double calorieLimit = Double.parseDouble(parts[4]);
                    dailyLog.addLogEntry(new CalorieLimitEntry(date, calorieLimit));
                } else {
                    String exerciseName = parts[4];
                    double minutesPassed = Double.parseDouble(parts[5]);
                    dailyLog.addLogEntry(new ExerciseEntry(date, exerciseName, minutesPassed));
                }
            }
        }
    }

    /**
     * Saving/Refreshing the entry logs.
     * 
     * @param type     - type of action.
     * @param toAppend - append/overwrite.
     * @param newLog   - new entry.
     */
    public void saveOrRefreshDLogDb(String dbName, String type, boolean toAppend, IEntryType newLog) {
        String filePath = String.format(DB_DIR, dbName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, toAppend))) {
            if (type.equals("REFRESH"))
                for (IEntryType entry : dailyLog.logEntries)
                    writer.write(entry.toString());
            else
                writer.write(newLog.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removing an entry from the file.
     * 
     * @param entryNameType - entry name/type.
     * @param entryAmount   - entry amount.
     */
    public void removeDLogEntry(Object[] paramArr) {
        String entryNameType = (String) paramArr[0];
        Double entryAmount = (Double) paramArr[1];

        for (int i = 0; i < dailyLog.logEntries.size(); i++) {
            IEntryType entry = dailyLog.logEntries.get(i);
            if (entry instanceof FoodEntry) {
                FoodEntry fEntry = (FoodEntry) entry;
                if (fEntry.getFoodName().equals(entryNameType) && fEntry.getAmount() == entryAmount) {
                    dailyLog.logEntries.remove(i);
                    break;
                }
            } else if (entry instanceof WeightEntry) {
                WeightEntry wEntry = (WeightEntry) entry;
                if (wEntry.getType().equals(entryNameType) && wEntry.getAmount() == entryAmount)
                    dailyLog.logEntries.remove(i);
            } else if (entry instanceof CalorieLimitEntry) {
                CalorieLimitEntry cEntry = (CalorieLimitEntry) entry;
                if (cEntry.getType().equals(entryNameType) && cEntry.getAmount() == entryAmount)
                    dailyLog.logEntries.remove(i);
            } else {
                ExerciseEntry eEntry = (ExerciseEntry) entry;
                if (eEntry.getExerciseName().equals(entryNameType) && eEntry.getAmount() == entryAmount) {
                    dailyLog.logEntries.remove(i);
                    break;
                }
            }
        }

        this.saveOrRefreshDLogDb("log", "REFRESH", false, null);
    }

    // ---------- FOOD_LOG_VBOX DATA MANIPULATION ---------- //
    /**
     * Loading the log entries.
     */
    public void loadFLogsFromDb() {
        if (!foodLog.logEntries.isEmpty())
            foodLog.logEntries.clear();

        List<String> foodList = instance.getData("foods");
        for (String fLog : foodList) {
            String[] logParts = fLog.split(",");

            String fType = logParts[0];
            String fName = logParts[1];
            if (fType.equals("b")) {
                double calories = Double.parseDouble(logParts[2]);
                double fats = Double.parseDouble(logParts[3]);
                double carbs = Double.parseDouble(logParts[4]);
                double protein = Double.parseDouble(logParts[5]);
                foodLog.addFoodEntry(new BasicType(fName, calories, fats, carbs, protein));
                continue;
            }

            String recipeData = fLog.substring(fName.length() + 3);
            foodLog.addFoodEntry(new RecipeType(fName, recipeData));
        }
    }

    /**
     * Adding a new food log to the database.
     * 
     * @param newLog - new food log.
     */
    public void addFLogToDb(IFoodEntryType newLog) {
        String filePath = String.format(DB_DIR, dbNames[1]);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(newLog.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------- EXERCISE_LOG_VBOX DATA MANIPULATION ---------- //
    /**
     * Loading logs from the database.
     */
    public void loadELogsFromDb() {
        if (!exerciseLog.logEntries.isEmpty())
            exerciseLog.logEntries.clear();

        List<String> exerciseList = instance.getData("exercise");
        for (String exercise : exerciseList) {
            String[] logParts = exercise.split(",");

            String name = logParts[1];
            double calories = Double.parseDouble(logParts[2]);
            exerciseLog.addExerciseLog(new ExerciseType(name, calories));
        }
    }

    /**
     * Adding a new exercise log to the exercise database.
     * 
     * @param newExercise - new exercise log.
     */
    public void addELogToDb(IExerciseType newExercise) {
        String fiePath = String.format(DB_DIR, dbNames[2]);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fiePath, true))) {
            writer.write(newExercise.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adding a new exercise log to the log database.
     * 
     * @param newExercise - new exercise log.
     */
    public void addEEntryToDb(IEntryType newExercise) {
        String fiePath = String.format(DB_DIR, dbNames[3]);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fiePath, true))) {
            writer.write(newExercise.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
