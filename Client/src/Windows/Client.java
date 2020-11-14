package Windows;

import java.io.*;
import java.net.Socket;

public class Client {


    private static final int PORT = 8818;
    private static final String HOST = "localhost";
    private static Socket clientSocket; //сокет для соединения
    private static BufferedReader consoleReader; // буфер для чтения из консоли

    public static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public Client() {
        start();
    }

    public boolean login (String userName, String password) throws IOException {
        String login = userName + " " + password;
        out.write("login " + login + "\n");
        out.flush();
        System.out.println("...waiting for server' answer...");
        String serverAnswer = in.readLine();
        System.out.println(serverAnswer);
        if (!serverAnswer.equalsIgnoreCase("Login error")) {
//        if (serverAnswer.equalsIgnoreCase("ok login")) {
            System.out.println("Authorization completed successfully! Let start chatting >>>");
            return true;
        }

        return false;
    }

    public boolean registration(String userName, String password) throws IOException {
        String login = userName + " " + password;
        System.out.println(login);
        out.write("registration " + login + "\n");
        out.flush();
        String serverAnswer = in.readLine();
        System.out.println(serverAnswer);

        if (serverAnswer.equalsIgnoreCase("New user registered " + "login: " + userName + ", password: " + password)) {

            System.out.println("Authorization completed successfully! Let start chatting >>>");
            login(userName, password);
            return true;
        } else {
            System.out.println("Authorization failed. Username or password is not correct >>> quit");
            return false;
        }
    }

    public String getMessage () throws IOException {

        String serverAnswer = in.readLine();

        return serverAnswer;

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
