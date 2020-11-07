import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    User user1 = new User("user", "user");
    User user2 = new User("guest", "guest");

    static List<User> userList = new ArrayList();

    public void initialiseUserList() {
        userList.add(user1);
        userList.add(user2);
    }


    public void addNewUser(User user) throws IOException {
        userList.add(user);
        FileOutputStream fos = new FileOutputStream("/C:/Users/User/Desktop/UserBase.tmp/");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(userList);
        oos.close();
    }


    public List<User> getUserList() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream("/C:/Users/User/Desktop/UserBase.tmp/");
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<User> userList = (List<User>) ois.readObject();
        ois.close();
        return userList;
    }


}
