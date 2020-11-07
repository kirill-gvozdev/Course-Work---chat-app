import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerRunner extends Thread {
    private final Socket clientSocket;
    private final Peer peer;
    private String login = null;
    OutputStream outputStream;
    boolean onlineStatus = false;


    UserDatabase db = new UserDatabase();


    public ServerRunner(Peer peer, Socket clientSocket) {
        this.peer = peer;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    private void handleClientSocket() throws IOException, ClassNotFoundException {
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
                    if (!onlineStatus) {
                        String msg = "User not logged in" +"\n";
                        outputStream.write(msg.getBytes());
                    } else {
                        messenger(tokens);
                    }

                } else if ("registration".equalsIgnoreCase(cmd)) {
                    registration(outputStream, tokens);
                }

            }
        }
        clientSocket.close();
    }

    private void registration(OutputStream outputStream, String[] tokens) throws IOException, ClassNotFoundException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];
            User newUser = new User(login, password);
            db.addNewUser(newUser);
            //db.updateDB();
            String msg = "New user registered " + "login: " + login + ", password: " + password +"\n";
            outputStream.write(msg.getBytes());
        } else {
            String msg = "Registration failed\n";
            outputStream.write(msg.getBytes());
        }
    }

    private void logOff() throws IOException {
        List<ServerRunner> serverRunners = peer.getServerRunners();
        System.out.println(login);
        String logoffMsg = "offline " + login + "\n";
        for (ServerRunner runner : serverRunners) {
            runner.broadcast(logoffMsg);
        }
        System.out.println(logoffMsg);
        onlineStatus = false;
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

    private boolean handleLogin(OutputStream outputStream, String[] tokens) throws IOException, ClassNotFoundException {

        User connectedUser = null;

        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            for (int i = 0; i < db.getUserList().size(); i++) {
                if (login.equals(db.getUserList().get(i).getName()) && password.equals(db.getUserList().get(i).getPassword())) {
                    connectedUser = db.getUserList().get(i);
                    if (connectedUser != null) {
                        String msg = "ok login\n";
                        outputStream.write(msg.getBytes());
                        this.login = login;
                        System.out.println("User logged in: " + login);

                        List<ServerRunner> serverRunners = peer.getServerRunners();

                        for (ServerRunner runner : serverRunners) {
                            if (!login.equals(runner.login)) {
                                String currentConnection = "online " + runner.getLogin() + "\n";
                                broadcast(currentConnection);
                            }
                        }
                        String userOnlineStatus = "online " + login + "\n";
                        for (ServerRunner runner : serverRunners) {
                            runner.broadcast(userOnlineStatus);
                        }
                        onlineStatus = true;
                        return true;
                    } else {
                        String msg = "Login error\n";
                        outputStream.write(msg.getBytes());
                    }
                }

            }
        }
        return false;
    }

    private void broadcast(String onlineStatus) throws IOException {
        outputStream.write(onlineStatus.getBytes());
    }
}