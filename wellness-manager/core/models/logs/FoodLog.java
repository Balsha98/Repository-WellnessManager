package core.models.logs;

import java.util.*;

import core.models.Model;
import core.interfaces.IFoodEntryType;

public class FoodLog {

    // Singleton instance.
    private static FoodLog instance;

    // Attributes.
    public List<IFoodEntryType> logEntries;

    // Default constructor.
    private FoodLog() {
        logEntries = new ArrayList<>();
    }

    // Singleton instantiation.
    public static FoodLog getInstance() {
        if (instance == null) {
            instance = new FoodLog();
        }

        return instance;
    }

    /**
     * Checking whether the log already exists.
     * 
     * @param newLog - new entry log.
     */
    public boolean doesItemExist(IFoodEntryType newLog) {
        if (this.logEntries.isEmpty())
            return false;

        for (IFoodEntryType item : this.logEntries)
            if (item.getName().equals(newLog.getName()))
                return true;

        return false;
    }

    /**
     * Adding an entry.
     * 
     * @param fEntryType - type of entry.
     */
    public void addFoodEntry(IFoodEntryType fEntryType) {
        logEntries.add(fEntryType);
    }

    /**
     * Getting hte log entries.
     * 
     * @return - log of entries.
     */
    public List<IFoodEntryType> getLogEntries() {
        Model.getInstance().loadFLogsFromDb();
        return logEntries;
    }
}
