import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        User user1 = new User("user", "user");
        User user2 = new User("guest", "guest");
        List<User> userList = new ArrayList();

        userList.add(user1);
        userList.add(user2);

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).getName() + userList.get(i).getPassword());

            }
        }
    }

