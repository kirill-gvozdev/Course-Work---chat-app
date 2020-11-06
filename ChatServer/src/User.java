import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
