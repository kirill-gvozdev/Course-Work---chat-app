import java.io.*;
import java.net.Socket;

public class Client {


    private static final int PORT = 4999;
    private static final String HOST = "localhost";
    private static Socket clientSocket; //сокет для соединения
    private static BufferedReader consoleReader; // буфер для чтения из консоли

    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    private boolean login () throws IOException {
        System.out.println("Enter username and password separated by a space: ");
        String login = consoleReader.readLine();
        //out.write("login " + login + "\n");
        out.write(login + "\n");
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
            System.out.print(">>>> ");
            String msg = consoleReader.readLine();
            out.write("send " + msg + "\n");
            out.flush();
            String serverAnswer = in.readLine();
            System.out.println(serverAnswer);
        }
    }

    public static void main(String[] args) {

        Client client = new Client();

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
}
