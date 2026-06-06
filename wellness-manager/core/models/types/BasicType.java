package core.models.types;

import core.interfaces.IFoodEntryType;

public class BasicType implements IFoodEntryType {

    // Attributes.
    private static final String INITIAL = "b";
    private String itemName;
    private double calories, fats, carbs, protein;

    /**
     * Parameterized constructor.
     * 
     * @param n  - item name;
     * @param cl - calories.
     * @param f  - fats.
     * @param cb - carbs.
     * @param p  - protein.
     */
    public BasicType(String n, double cl, double f, double cb, double p) {
        itemName = n;
        calories = cl;
        fats = f;
        carbs = cb;
        protein = p;
    }

    // Inherited method.
    public String getName() {
        return itemName;
    }

    // Inherited method.
    public String toString() {
        return String.format(
                "%s,%s,%.1f,%.1f,%.1f,%.1f%n",
                INITIAL,
                itemName,
                calories,
                fats,
                carbs,
                protein);
    }
}
