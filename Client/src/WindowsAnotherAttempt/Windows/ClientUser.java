package WindowsAnotherAttempt.Windows;

import java.io.*;
import java.net.Socket;

public class ClientUser {

    //private static String msg;
    private static boolean chattingOn = true;

    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static ChatWindow log;

//    private UserWindow connectionListener;
    private final Socket socket;

    private final Thread thread; // слушает входящие сообщения (постоянно читает поток ввода), если строчка прилетела - генерирует событие



    public ClientUser (String host, int port) throws IOException {
        this(new Socket(host, port));
    }
    public ClientUser (Socket socket) throws IOException {

        //сокет для соединения
        this.socket = socket;

        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        thread = new Thread(new Runnable() { // через анонимный класс Runnable
            @Override
            public void run() { // метод run слушает входящее соединение

                    while (!thread.isInterrupted()) {
                        if (chattingOn) {
                            try {
                                receiveMessage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                }
            }
        }); // генерация нового потока
        thread.start(); // запуск потока


    }

    public void setLog (ChatWindow chatWindow) {
         log = chatWindow;
         chattingOn = true;
    }

    public boolean authorization (String userName, String password) throws IOException {
            out.write("login " + userName + " " + password + "\n");
            out.flush();
            String serverAnswer = in.readLine();
            if (serverAnswer.equalsIgnoreCase("ok login")) {
                return true;
            }
            return false;
    }

    public String registration (String userName, String password) throws IOException {
        out.write("registration " + userName + " " + password +  "\n");
        out.flush();
        return in.readLine();
    }

    public void sendMessage (String msg) throws IOException{
        out.write(msg);
        out.flush();
    }

    public void receiveMessage () throws IOException{
        log.printMsg(in.readLine());
    }

    public void sendQuit () throws IOException{
        out.write("quit" + "\n");
        out.flush();
        chattingOn = false;
        socket.close();
        in.close();
        out.close();
    }

}