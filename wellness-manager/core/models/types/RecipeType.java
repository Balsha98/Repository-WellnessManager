package core.models.types;

import core.interfaces.IFoodEntryType;

public class RecipeType implements IFoodEntryType {

    // Attributes.
    private static final String INITIAL = "r";
    private String recipeName, recipeData;

    /**
     * Parameterized constructor.
     * 
     * @param n - recipe name.
     */
    public RecipeType(String n, String d) {
        recipeName = n;
        recipeData = d;
    }

    // Inherited method.
    public String getName() {
        return recipeName;
    }

    /**
     * Adding an item to the recipe.
     * 
     * @param itemName - item name.
     * @param amount   - item amount.
     * @return - true/false.
     */
    public void addItem(String itemName, double amount) {
        recipeData += (String.format(
                "%s,%.1f,", itemName, amount));
    }

    // Inherited method.
    public String toString() {
        if (recipeData.endsWith(","))
            recipeData = recipeData.substring(0, recipeData.length() - 1);

        return String.format(
                "%s,%s,%s%n",
                INITIAL,
                recipeName,
                recipeData);
    }
}
