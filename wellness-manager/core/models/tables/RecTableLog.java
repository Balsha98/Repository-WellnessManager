package core.models.tables;

import javafx.beans.property.*;

// Class used for the Recipe Table.
public class RecTableLog {

    // Column values.
    private SimpleStringProperty itemName;
    private SimpleDoubleProperty itemCount;

    // Parameterized constructor.
    public RecTableLog(String itemName, double itemCount) {
        this.itemName = new SimpleStringProperty(itemName);
        this.itemCount = new SimpleDoubleProperty(itemCount);
    }

    // Getting the item name.
    public SimpleStringProperty getName() {
        return this.itemName;
    }

    // Getting the item count.
    public SimpleDoubleProperty getCount() {
        return this.itemCount;
    }
}
