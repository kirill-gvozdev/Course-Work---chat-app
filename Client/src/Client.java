//import java.io.*;
//import java.net.Socket;
//
//public class ClientGuestTwo {
//
//
//    private static final int PORT = 8818;
//    private static final String HOST = "localhost";
//    private static Socket clientSocket; //сокет для соединения
//    private static BufferedReader consoleReader; // буфер для чтения из консоли
//
//    private static BufferedReader in; // поток чтения из сокета
//    private static BufferedWriter out; // поток записи в сокет
//
//    public boolean login () throws IOException {
//        System.out.println("Enter username and password separated by a space: ");
//        String login = consoleReader.readLine();
//        out.write("login " + login + "\n");
//        out.flush();
//        System.out.println("...waiting for server' answer...");
//        String serverAnswer = in.readLine();
//        if (serverAnswer.equalsIgnoreCase("ok login")) {
//            System.out.println("Authorization completed successfully! Let start chatting >>>");
//            return true;
//        }
//        else {
//            out.write("registration " + login + "\n");
//            out.flush();
//            if (!serverAnswer.equalsIgnoreCase("Registration failed")) {
//                System.out.println("Authorization completed successfully! Let start chatting >>>");
//                return true;
//            } else {
//                System.out.println("Authorization failed. Username or password is not correct >>> quit");
//                return false;
//            }
//        }
//    }
//
//
////
////
////        if (consoleReader.ready()) {
////            String msg = consoleReader.readLine();
////            if (!msg.equals("")) {
////                if (msg.equals("quit")) out.write(msg);
////                else out.write("send " + msg + "\n");
////                out.flush();
////            }
////        }
//
//    private void getMessage () throws IOException {
//
//        if (in.ready()) {
//            String serverAnswer = in.readLine();
//            System.out.println(serverAnswer);
//        }
//
//    }
//
//    private boolean sendMessage () throws IOException {
//        if (consoleReader.ready()) {
//            String msg = consoleReader.readLine();
//            if (!msg.equals("")) {
//                if (msg.equals("quit")) {
//                    out.write(msg);
//                    out.flush();
//                    return false;
//                } else {
//                    out.write("send " + msg + "\n");
//                    out.flush();
//                }
//            }
//        }
//        return true;
//    }
//
//    public static void main(String[] args) {
//
//        ClientGuestTwo client = new ClientGuestTwo();
//
//        consoleReader = new BufferedReader(new InputStreamReader(System.in));
//
//
//        try {
//            clientSocket = new Socket(HOST, PORT);
//            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            boolean chatting = true;
//            if (client.login()) {
//                while (chatting) {
//                    client.getMessage();
//                    chatting = client.sendMessage();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                clientSocket.close();
//                in.close();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//public static class ClientUserOld {
//
//    private static BufferedReader in; // поток чтения из сокета
//    private static BufferedWriter out; // поток записи в сокет
//
//    private UserWindow connectionListener;
//    private final Socket socket;
//
//    private final Thread thread; // слушает входящие сообщения (постоянно читает поток ввода), если строчка прилетела - генерирует событие
//
//
//
//    public ClientUserOld(UserWindow messenger, String host, int port) throws IOException {
//        this(messenger, new Socket(host, port));
//    }
//    public ClientUserOld(UserWindow messenger, Socket socket) throws IOException {
//
//        //сокет для соединения
//        this.socket = socket;
//        this.connectionListener = messenger;
//
//        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//        thread = new Thread(new Runnable() { // через анонимный класс Runnable
//            @Override
//            public void run() { // метод run слушает входящее соединение
//                try {
//                    // TODO здесь не работает!!!
//                    //todo вызвать метод, возвращающий имя юзера
//                    String msg1 = "login " + messenger.userName + " " + messenger.userName;
//                    System.out.println(messenger.userName);
//                    out.write(msg1);
//                    out.flush();
//
////                    String serverAnswer = getMessage();
//
//                    // далее в бесконечном цикле
//                    // пока поток-нить thread не прерван читаем строки из буфера
//
//                    while (!thread.isInterrupted()) {
//                        String msg = in.readLine();
//                        System.out.println(msg);
////                        connectionListener.getMessageFromClient(ClientUserOld.this, msg); // строку сообщения - юзеру
//
//                    }
//                } catch (IOException e) {
////                    connectionListener.onException(ClientUserOld.this, e);
//                } finally {
////                    connectionListener.onDisconnect(ClientUserOld.this); // сообщение о дисконнекте
//
//                    try {
//                        socket.close();
//                        in.close();
//                        out.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//
//                    }
//
//                }
//            }
//        }); // генерация нового потока
//        thread.start(); // запуск потока
//
//
////    private void login (String log) throws IOException {
////        out.write("login " + log + " " + log + "\n");
////        out.flush();
////        String serverAnswer = getMessage();
////        if (serverAnswer.equalsIgnoreCase("ok login")) {
////            //TODO System.out.println("Authorization completed successfully! Let start chatting >>>");
////            return true;
////        }
////        else {
////            //TODO System.out.println("Authorization failed. Username or password is not correct >>> quit");
////            return false;
////        }
//    }
//
//    public boolean sendMessage (String msg) throws IOException{
//        out.write("login " + msg + "\n");
//        out.flush();
//        String serverAnswer = in.readLine();
//        if (serverAnswer.equalsIgnoreCase("ok login")) {
//            return true;
//        }
//        return false;
//    }
//
//    public void sendQuit (String msg) {
//            try {
//                out.write(msg);
//                out.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//    }
//
//
//    public String getMessage () {
//        try {
//            return in.readLine();
//        } catch (IOException e) {
//            return "Connection exception" + e;
//        }
//    }
//
//
//}}
