import java.io.*;
import java.net.Socket;

public class ClientTwo {


    private static final int PORT = 8818;
    private static final String HOST = "localhost";
    private static Socket clientSocket; //сокет для соединения
    private static BufferedReader consoleReader; // буфер для чтения из консоли

    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    private boolean login () throws IOException {
        System.out.println("Enter username and password separated by a space: ");
        String login = consoleReader.readLine();
        out.write("login " + login + "\n");
        //out.write(login + "\n");
        out.flush();
        System.out.println("...waiting for server' answer...");
        String serverAnswer = in.readLine();
        if (serverAnswer.equalsIgnoreCase("ok login")) {
            System.out.println("Authorization completed successfully! You can start chatting >>>");
            return true;
        }
        else {
            System.out.println("Authorization failed. Username or password is not correct >>> quit");
            return false;
        }
    }

    private void chatting () throws IOException {
        while (!clientSocket.isClosed()) {
            String serverAnswer = null;
            while (serverAnswer == null) {
                serverAnswer = in.readLine();
            }
            System.out.print(serverAnswer);
            System.out.print(">>>> ");
            String msg = consoleReader.readLine();
            if (msg.equals("quit")) {
                out.write(msg);
                out.flush();
                break;
            }
            out.write("send " + msg + "\n");
            out.flush();
        }
    }

    public static void main(String[] args) {

        ClientTwo client = new ClientTwo();

        consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            clientSocket = new Socket(HOST, PORT);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            if (client.login()) {
                client.chatting();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //out.write("quit\n");
                //out.flush();
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
