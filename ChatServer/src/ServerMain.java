public class  ServerMain {

    public static void main(String[] args) {
        int port = 8818;
        Peer peer = new Peer(port);
        peer.start();
    }
}


