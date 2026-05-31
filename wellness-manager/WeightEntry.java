import java.time.LocalDate;

public class WeightEntry implements IEntryType {

    // Attributes.
    private LocalDate date;
    public static final String TYPE = "Weight Goal";
    private double weight;

    /**
     * Parameterized constructor.
     * 
     * @param date   - date added.
     * @param weight - the weight.
     */
    public WeightEntry(LocalDate date, double weight) {
        this.date = date;
        this.weight = weight;
    }

    // Inherited method.
    public LocalDate getDate() {
        return date;
    }

    // Inherited method.
    public String getType() {
        return TYPE;
    }

    // Inherited method.
    public double getAmount() {
        return weight;
    }

    // Inherited method.
    public String toString() {
        String[] dateValues = dateFormatter.format(date).split("-");
        return String.format("%s,%s,%s,w,%.1f%n",
                dateValues[0],
                dateValues[1],
                dateValues[2],
                this.getAmount());
    }
}
