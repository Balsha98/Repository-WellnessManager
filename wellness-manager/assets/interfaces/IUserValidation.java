import java.util.List;

public interface IUserValidation {

    /**
     * Getting the validated user.
     * 
     * @return - the user.
     */
    public User getUser();

    /**
     * Validating user info.
     * 
     * @return - true/false.
     */
    public boolean validateUser(List<String> dbData);

    /**
     * Checking for duplicates.
     * 
     * @return - true/false.
     */
    public boolean isUnique(List<String> dbData);
}
