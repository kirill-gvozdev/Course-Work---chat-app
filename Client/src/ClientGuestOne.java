import java.io.*;
import java.net.Socket;

public class ClientGuestOne {


    private static final int PORT = 8818;
    private static final String HOST = "localhost";
    private static Socket clientSocket; //сокет для соединения
    private static BufferedReader consoleReader; // буфер для чтения из консоли

    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public boolean login () throws IOException {
        System.out.println("Enter username and password separated by a space: ");
        String login = consoleReader.readLine();
        out.write("login " + login + "\n");
        out.flush();
        System.out.println("...waiting for server' answer...");
        String serverAnswer = in.readLine();
        if (serverAnswer.equalsIgnoreCase("ok login")) {
            System.out.println("Authorization completed successfully! Let start chatting >>>");
            return true;
        }
        else {
            out.write("registration " + login + "\n");
            out.flush();
            if (!serverAnswer.equalsIgnoreCase("Registration failed")) {
                System.out.println("Authorization completed successfully! Let start chatting >>>");
                return true;
            } else {
                System.out.println("Authorization failed. Username or password is not correct >>> quit");
                return false;
            }
        }
    }

    private void getMessage () throws IOException {

        if (in.ready()) {
            String serverAnswer = in.readLine();
            System.out.println(serverAnswer);
        }

    }

    private boolean sendMessage () throws IOException {
        if (consoleReader.ready()) {
            String msg = consoleReader.readLine();
            if (!msg.equals("")) {
                if (msg.equals("quit")) {
                    out.write(msg);
                    out.flush();
                    return false;
                } else {
                    out.write("send " + msg + "\n");
                    out.flush();
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        ClientGuestOne client = new ClientGuestOne();

        consoleReader = new BufferedReader(new InputStreamReader(System.in));


        try {
            clientSocket = new Socket(HOST, PORT);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            boolean chatting = true;
            if (client.login()) {
                while (chatting) {
                    client.getMessage();
                    chatting = client.sendMessage();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
