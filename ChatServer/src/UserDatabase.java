import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    User user1 = new User("user", "user");
    User user2 = new User("guest", "guest");

    List<User> userList = new ArrayList();

    public void initialiseUserList() {
        userList.add(user1);
        userList.add(user2);
    }

    public List<User> getUserList() {
        return userList;
    }



}
