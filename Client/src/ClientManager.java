//import java.io.*;
//import java.net.Socket;
//
//public class ClientManager {
//
//    private static BufferedReader in; // поток чтения из сокета
//    private static BufferedWriter out; // поток записи в сокет
//
//    private UserWindow userWindow;
//    private final Socket socket;
//
//    private final Thread thread; // слушает входящие сообщения (постоянно читает поток ввода), если строчка прилетела - генерирует событие
//
//
//
//    public ClientManager(UserWindow messenger, String host, int port) throws IOException {
//        this(messenger, new Socket(host, port));
//    }
//    public ClientManager(UserWindow messenger, Socket socket) throws IOException {
//
//        //сокет для соединения
//        this.socket = socket;
//        this.userWindow = messenger;
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
//                        //userWindow.getMessageFromClient(ClientManager.this, msg); // строку сообщения - юзеру
//
//                    }
//                } catch (IOException e) {
//                   // userWindow.onException(ClientManager.this, e);
//                } finally {
//                    //userWindow.onDisconnect(ClientManager.this); // сообщение о дисконнекте
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
//    }
//
//        public void login (String loginMessage) throws IOException {
//            out.write("login " + loginMessage + "\n");
//            out.flush();
//            System.out.println("...waiting for server' answer...");
//            String serverAnswer = in.readLine();
//            if (serverAnswer.equalsIgnoreCase("ok login")) {
//                System.out.println("Authorization completed successfully! Let start chatting >>>");
//            }
//            else {
//                //out.write("registration " + login + "\n");
//                out.flush();
//                if (!serverAnswer.equalsIgnoreCase("Registration failed")) {
//                    System.out.println("Authorization completed successfully! Let start chatting >>>");
//                } else {
//                    System.out.println("Authorization failed. Username or password is not correct >>> quit");
//                }
//            }
//        }
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
//
//    public void sendMessage (String msg) {  //throws IOException {
//                if (msg.equals("quit\n")) {
//                    try {
//                        out.write(msg);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        out.write("send " + msg + "\n");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//        try {
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
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
//    private String getMessage () {
//        try {
//            return in.readLine();
//        } catch (IOException e) {
//            return "Connection exception" + e;
//        }
//    }
//
//
//}