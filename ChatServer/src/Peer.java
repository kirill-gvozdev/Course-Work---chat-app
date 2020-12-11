import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Peer extends Thread{
    private final int port;
    private List<ServerRunner> serverRunners= new ArrayList<>();

    public Peer(int port) {
        this.port = port;
    }

    public List<ServerRunner> getServerRunners() {
        return serverRunners;
    }


    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                ServerRunner runner = new ServerRunner(this, clientSocket);
                serverRunners.add(runner);
                runner.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeClient(ServerRunner serverRunner) {
        serverRunners.remove(serverRunner);
    }
}
