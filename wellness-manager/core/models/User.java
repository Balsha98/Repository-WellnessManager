package core.models;

import java.util.List;
import core.interfaces.IUserValidation;

public class User implements IUserValidation {

    // Attributes.
    private String userName, password;

    /**
     * Parameterized constructor.
     * Dependency Injection
     * 
     * @param n - username.
     * @param p - password.
     */
    public User(String n, String p) {
        userName = n;
        password = p;
    }

    // Inherited method.
    public User getUser() {
        return null;
    }

    /**
     * Getting the username.
     * 
     * @return - the username.
     */
    public String getName() {
        return userName;
    }

    /**
     * Getting hte password.
     * 
     * @return - the password.
     */
    public String getPass() {
        return password;
    }

    // Inherited method.
    public boolean validateUser(List<String> dbData) {
        return false;
    }

    // Inherited method.
    public boolean isUnique(List<String> dbData) {
        return false;
    }

    /**
     * String representation of the user info.
     * 
     * @return - a String.
     */
    public String toString() {
        return String.format(
                "%s,%s%n",
                userName,
                password);
    }
}
