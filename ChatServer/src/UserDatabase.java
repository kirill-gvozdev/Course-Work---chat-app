import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDatabase {

    User user1 = new User("user", "user");
    User user2 = new User("guest", "guest");

    static List<User> userList = new ArrayList();

    public void initialiseUserList() {
        userList.add(user1);
        userList.add(user2);
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileOutputStream fos = new FileOutputStream("/C:/Users/User/Desktop/UserBase.tmp/");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(userList);
        oos.close();

        FileInputStream fis = new FileInputStream("/C:/Users/User/Desktop/UserBase.tmp/");
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<User> userList = (List<User>) ois.readObject();
        ois.close();
    }



    public void addNewUser(User user) {
        userList.add(user);
    }

    public List<User> getUserList() {
        return userList;
    }



}
