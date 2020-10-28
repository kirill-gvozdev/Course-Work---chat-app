import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerRunner extends Thread {
    private final Socket clientSocket;
    private final Peer peer;
    private String login = "";
    OutputStream outputStream;

    User user1 = new User("user", "user");
    User user2 = new User("guest", "guest");



    List<User> userList = new ArrayList();


    public ServerRunner(Peer peer, Socket clientSocket) {
        this.peer = peer;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    private void handleClientSocket() throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");
            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if ("quit".equalsIgnoreCase(cmd)) {
                    logOff();
                    break;
                } else if ("login".equalsIgnoreCase(cmd)) {
                    handleLogin(outputStream, tokens);
                } else if ("send".equalsIgnoreCase(cmd)) {
                    messenger(tokens);
                }

                }
            }
        clientSocket.close();
    }

    private void logOff() {
        System.out.println(login + "logged off");
        peer.removeClient(this);
    }

    private void messenger(String[] tokens) throws IOException {
        List<ServerRunner> serverRunners = peer.getServerRunners();
        tokens[0] = "";
        String str = String.join(" ", tokens);

        for (ServerRunner runner : serverRunners) {
            String msg = login + " says " + " " + str + "\n";
            runner.broadcast(msg);
        }
    }

    private boolean handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if (login.equals(user1.getName()) && password.equals(user1.getPassword())) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User logged in: " + login + "\n");
                userList.add(user1);
                user1.setOnline(true);

                List<ServerRunner> serverRunners = peer.getServerRunners();

                for (ServerRunner runner: serverRunners) {
                    if (!login.equals(runner.getLogin()) && runner.getLogin() != null) {
                        String currentConnection = "online " + runner.getLogin() + "\n";
                        broadcast(currentConnection);
                    }
                }
                String onlineStatus = "online " + login + "\n";
                for (ServerRunner runner: serverRunners) {
                    if (!login.equals(runner.getLogin())) {
                        runner.broadcast(onlineStatus);
                    }
                }

                return true;
            } else if (login.equals(user2.getName()) && password.equals(user2.getPassword())) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User logged in: " + login + "\n");
                userList.add(user2);
                user2.setOnline(true);


                List<ServerRunner> serverRunners = peer.getServerRunners();

                for (ServerRunner runner: serverRunners) {
                    if (!login.equals(runner.getLogin()) && runner.getLogin() != null) {
                        String currentConnection = "online " + runner.getLogin() + "\n";
                        broadcast(currentConnection);
                    }
                }
                String onlineStatus = "online " + login + "\n";
                for (ServerRunner runner: serverRunners) {
                    if (!login.equals(runner.getLogin())) {
                        runner.broadcast(onlineStatus);
                    }
                }
                return true;
            } else {
                String msg = "Login error\n";
                outputStream.write(msg.getBytes());
            }
        }
        return false;
    }

    private void broadcast(String onlineStatus) throws IOException {
        if (login != null) {
            outputStream.write(onlineStatus.getBytes());
            outputStream.flush();
        }
    }
}