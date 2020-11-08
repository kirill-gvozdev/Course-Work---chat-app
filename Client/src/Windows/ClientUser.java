package Windows;

import java.io.*;
import java.net.Socket;

public class ClientUser {

    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

//    private UserWindow connectionListener;
    private final Socket socket;

    private final Thread thread; // слушает входящие сообщения (постоянно читает поток ввода), если строчка прилетела - генерирует событие



    public ClientUser (String host, int port) throws IOException {
        this(new Socket(host, port));
    }
    public ClientUser (Socket socket) throws IOException {

        //сокет для соединения
        this.socket = socket;
//        this.connectionListener = messenger;

        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        thread = new Thread(new Runnable() { // через анонимный класс Runnable 
            @Override
            public void run() { // метод run слушает входящее соединение
                try {
                    // TODO здесь не работает!!!
                    //todo вызвать метод, возвращающий имя юзера
//                    String msg1 = "login " + messenger.userName + " " + messenger.userName;
//                    System.out.println(messenger.userName);
//                    out.write(msg1);
//                    out.flush();

//                    String serverAnswer = getMessage();

                    // далее в бесконечном цикле
                    // пока поток-нить thread не прерван читаем строки из буфера

                    while (!thread.isInterrupted()) {
                        String msg = in.readLine();
                        System.out.println(msg);
//                        connectionListener.getMessageFromClient(ClientUser.this, msg); // строку сообщения - юзеру

                    }
                } catch (IOException e) {
//                    connectionListener.onException(ClientUser.this, e);
                } finally {
//                    connectionListener.onDisconnect(ClientUser.this); // сообщение о дисконнекте

                    try {
                        socket.close();
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }
            }
        }); // генерация нового потока
        thread.start(); // запуск потока


    }

    public boolean authorization (String msg) throws IOException {
            out.write("login " + msg + "\n");
            out.flush();
            String serverAnswer = in.readLine();
            if (serverAnswer.equalsIgnoreCase("ok login")) {
                return true;
            }
            return false;
    }

    public String registration (String msg) throws IOException {
        out.write("registration " + msg + "\n");
        out.flush();
        return in.readLine();
    }

    public void sendMessage (String msg) {
            try {
                out.write(msg);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}