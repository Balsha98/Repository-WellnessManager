package core.models.logs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import core.interfaces.IEntryType;

import core.models.Model;
import core.models.entries.CalorieLimitEntry;
import core.models.entries.WeightEntry;

public class DailyLog {

    // Singleton instance.
    private static DailyLog instance;

    // Attributes.
    public List<IEntryType> logEntries;

    // Default constructor.
    private DailyLog() {
        logEntries = new ArrayList<>();
    }

    // Singleton instantiation.
    public static DailyLog getInstance() {
        if (instance == null) {
            instance = new DailyLog();
        }

        return instance;
    }

    /**
     * Calculating the nutrients.
     * 
     * @param date - selected date.
     * @return - an array of nutrients.
     */
    public double[] calcNutrients(LocalDate date) {
        double nSum, fats = 0, carbs = 0, protein = 0;
        String name, amount;

        for (IEntryType log : logEntries) {
            String[] logValues = log.toString().split(",");

            if (logValues.length == 6) {
                name = logValues[4];
                amount = logValues[5];
                if (log.getDate().toString().equals(date.toString())) {
                    double count = Double.parseDouble(amount);

                    fats += getNutrient(name, count, 3);
                    carbs += getNutrient(name, count, 4);
                    protein += getNutrient(name, count, 5);
                }
            }
        }

        // Calculating the nutrients
        nSum = fats + carbs + protein;
        fats = fats / nSum * 100;
        carbs = carbs / nSum * 100;
        protein = protein / nSum * 100;

        return new double[] { fats, carbs, protein };
    }

    /**
     * Getting the nutrient.
     * 
     * @param itemName - item name.
     * @param amount   - # of items.
     * @param index    - nutrient's position.
     * @return - nutrient.
     */
    public double getNutrient(String itemName, double amount, int index) {
        double nutrient = 0;

        List<String> foodList = Model.getInstance().getData("foods");
        for (String food1 : foodList) {
            String[] foodValues1 = food1.split(",");
            String currType = foodValues1[0], currName = foodValues1[1];

            if (currType.equals("r") && currName.equals(itemName)) {
                for (int i = 2; i < foodValues1.length; i += 2) {
                    String ing = foodValues1[i];
                    double ingCount = Double.parseDouble(foodValues1[i + 1]);

                    for (String food2 : foodList) {
                        String[] foodValues2 = food2.split(",");

                        if (foodValues2[0].equals("r") && foodValues2[1].equals(ing))
                            return getNutrient(ing, ingCount, index);
                        else if (foodValues2[0].equals("b") && foodValues2[1].equals(ing))
                            nutrient += Double.parseDouble(foodValues2[index]) * ingCount;
                    }
                }
            } else if (currType.equals("b") && currName.equals(itemName)) {
                for (String food2 : foodList) {
                    String[] foodValues2 = food2.split(",");

                    if (foodValues2[1].equals(itemName))
                        nutrient += Double.parseDouble(foodValues2[index]) * amount;
                }
            }
        }

        return nutrient;
    }

    /**
     * Getting the most recent weight entry.
     * 
     * @param date - selected date.
     * @return - a default value.
     */
    public double getRecentWeight(LocalDate date) {
        // Get the most recent weight entry
        for (int i = logEntries.size() - 1; i >= 0; i--)
            if (logEntries.get(i) instanceof WeightEntry) {
                WeightEntry entry = (WeightEntry) logEntries.get(i);
                if (entry.getDate().toString().equals(date.toString()))
                    return entry.getAmount();
            }

        // If no weight entry for the given date, use default weight.
        return 150.0;
    }

    /**
     * Getting the most recent calorie entry.
     * 
     * @param date - selected date.
     * @return - a default value.
     */
    public double getRecentCalories(LocalDate date) {
        // Get the most recent calorie limit entry
        for (int i = logEntries.size() - 1; i >= 0; i--)
            if (logEntries.get(i) instanceof CalorieLimitEntry) {
                CalorieLimitEntry entry = (CalorieLimitEntry) logEntries.get(i);
                if (entry.getDate().toString().equals(date.toString()))
                    return entry.getAmount();
            }

        // If no calorie limit entry for the given date, use default limit.
        return 2000.0;
    }

    /**
     * Adding an entry.
     * 
     * @param entry - a new entry.
     */
    public void addLogEntry(IEntryType entry) {
        logEntries.add(entry);
    }

    // Getting the list of entries.
    public List<IEntryType> getLogEntries() {
        Model.getInstance().loadDLogsFromDb();
        return logEntries;
    }
}
