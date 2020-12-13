package Windows;

import java.io.*;
import java.net.Socket;

public class Client {


    private static final int PORT = 8818;
    private static final String HOST = "localhost";
    private static Socket clientSocket;

    public static BufferedReader in; // socket reading stream (buffer)
    private static BufferedWriter out; // socket writing stream (buffer)

    public Client() {
        start();
    }

    public boolean login (String userName, String password) throws IOException {
        String login = userName + " " + password;
        out.write("login " + login + "\n");
        out.flush();
        System.out.println("...waiting for server' answer...");
        String serverAnswer = in.readLine();
        if (!serverAnswer.equalsIgnoreCase("Login error")) {
            System.out.println("Authorization completed successfully! Let start chatting >>>");
            return true;
        }

        return false;
    }

    public boolean registration(String userName, String password) throws IOException {
        String login = userName + " " + password;
        out.write("registration " + login + "\n");
        out.flush();
        String serverAnswer = in.readLine();
        System.out.println(serverAnswer);

        if (serverAnswer.equalsIgnoreCase("New user registered " + "login: " + userName + ", password: " + password)) {

            System.out.println("Registration completed successfully!");
            login(userName, password);
            return true;
        } else {
            System.out.println("Registration failed. Username or password is not correct >>> quit");
            return false;
        }
    }

    public String getMessage () throws IOException {

        if (in.ready()) return in.readLine();

        return in.readLine();

    }

    public void sendMessage (String msg) throws IOException {
        out.write("send " + msg + "\n");
        out.flush();

    }

    public void start () {

        try {
            clientSocket = new Socket(HOST, PORT);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void finish () {
        try {
            out.write("quit\n");
            out.flush();
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
