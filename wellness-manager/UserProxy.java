import java.util.List;

public class UserProxy implements IUserValidation {

    // Attributes.
    private User user;

    // Parameterized constructor.
    public UserProxy(String n, String p) {
        user = new User(n, p);
    }

    /**
     * Getting the validated user.
     * 
     * @return - the user.
     */
    public User getUser() {
        return user;
    }

    // Inherited method.
    public boolean validateUser(List<String> dbData) {
        if (dbData.isEmpty())
            return false;

        for (String uData : dbData) {
            String[] dataParts = uData.split(",");
            if (dataParts[0].equals(user.getName())) {
                if (dataParts[1].equals(user.getPass()))
                    return true;

                return false;
            }
        }

        return false;
    }

    // Inherited method.
    public boolean isUnique(List<String> dbData) {
        if (dbData.isEmpty())
            return true;

        for (String uData : dbData) {
            String[] dataParts = uData.split(",");
            if (dataParts[0].equals(user.getName()))
                return false;
        }

        return true;
    }
}
