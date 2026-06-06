package core.interfaces;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface IEntryType {

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Getting the date.
     * 
     * @return - the date.
     */
    public LocalDate getDate();

    /**
     * Getting the type.
     * 
     * @return - the type.
     */
    public String getType();

    /**
     * Getting the amount.
     * 
     * @return - the amount.
     */
    public double getAmount();

    /**
     * Getting the formatted string.
     * 
     * @return - the formatted string.
     */
    public String toString();
}
