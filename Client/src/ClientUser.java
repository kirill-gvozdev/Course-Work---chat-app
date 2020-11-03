import java.io.*;
import java.net.Socket;

public class ClientUser {

    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    private UserWindow connectionListener;
    private final Socket socket;

    private final Thread thread; // слушает входящие сообщения (постоянно читает поток ввода), если строчка прилетела - генерирует событие



    public ClientUser (UserWindow messenger, String host, int port) throws IOException {
        this(messenger, new Socket(host, port));
    }
    public ClientUser (UserWindow messenger, Socket socket) throws IOException {

        //сокет для соединения
        this.socket = socket;
        this.connectionListener = messenger;

        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        thread = new Thread(new Runnable() { // через анонимный класс Runnable 
            @Override
            public void run() { // метод run слушает входящее соединение
                try {
                    // login
                    String msg1 = "login " + messenger.fieldNickname.getName() + " " + messenger.fieldNickname.getName();
                    System.out.println(msg1);
                    out.write(msg1);
//                    out.write("login " + messenger.fieldNickname.getName() + " " + messenger.fieldNickname.getName());
                    out.flush();

//                    String serverAnswer = getMessage();

                    // далее в бесконечном цикле
                    // пока поток-нить thread не прерван читаем строки из буфера

                    while (!thread.isInterrupted()) {
                        String msg = in.readLine();
                        System.out.println(msg);
                        connectionListener.getMessageFromClient(ClientUser.this, msg); // строку сообщения - юзеру

                    }
                } catch (IOException e) {
                    connectionListener.onException(ClientUser.this, e);
                } finally {
                    connectionListener.onDisconnect(ClientUser.this); // сообщение о дисконнекте

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


//    private void login (String log) throws IOException {
//        out.write("login " + log + " " + log + "\n");
//        out.flush();
//        String serverAnswer = getMessage();
//        if (serverAnswer.equalsIgnoreCase("ok login")) {
//            //TODO System.out.println("Authorization completed successfully! Let start chatting >>>");
//            return true;
//        }
//        else {
//            //TODO System.out.println("Authorization failed. Username or password is not correct >>> quit");
//            return false;
//        }
    }

    public void sendMessage (String msg) {  //throws IOException {
                if (msg.equals("quit\n")) {
                    try {
                        out.write(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        out.write("send " + msg + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendQuit (String msg) {
            try {
                out.write(msg);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    private String getMessage () {
        try {
            return in.readLine();
        } catch (IOException e) {
            return "Connection exception" + e;
        }
    }


}