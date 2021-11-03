
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.util.ArrayList;

public class ChatServer {

    static ArrayList<Socket> clients = new ArrayList<Socket>();

    public static void main(String args[]) {

        System.out.println("Server starting, awaiting clients..");

        ServerClientDistributor scd = new ServerClientDistributor();
        scd.setSelf(scd);

        try {
            scd.clientDistributor();
        } catch (Exception e) {
            System.out.println("Something wrong 1: \n" + e);
        }
        System.out.println("Server shutting down..");

    }
}

class ServerClientDistributor {

    private static ArrayList<Socket> clients = new ArrayList<Socket>();
    private static ServerClientDistributor self;

    public static void clientDistributor() throws Exception {
        ServerSocket socket = new ServerSocket(1337);
        Socket client;
        ServerClientCommunicator scc;
        Thread thread;

        int i = 0;
        while (true) {
            client = socket.accept();

            clients.add(client);
            scc = new ServerClientCommunicator(client, i++, self);
            thread = new Thread(scc);
            thread.start();
        }
    }

    ArrayList<Socket> getClients() {
        return clients;
    }

    void setSelf(ServerClientDistributor self) {
        this.self = self;
    }
}

class ServerClientCommunicator implements Runnable {

    ServerClientDistributor distributor;
    Socket socket;
    int id;

    ServerClientCommunicator(Socket socket, int id, ServerClientDistributor distributor) {
        this.socket = socket;
        this.id = id;
        this.distributor = distributor;
    }

    @Override
    public void run() {

        System.out.println("Runnable starting with id: " + id);
        try {
            doStuff();
        } catch (Exception e) {
            System.out.println("Something wrong 2 \n" + e);
        }
        System.out.println("Runnable shutting down with id:" + id);

    }

    private void doStuff() throws Exception {

        InputStream fromClient = socket.getInputStream();
        ArrayList<Socket> clients;

        int b = fromClient.read();
        while (b != -1) {
            if (b == 6) {
                b = fromClient.read();
                continue;
            }
            //System.out.print((char)b);
            clients = distributor.getClients();

            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i) == socket || clients.get(i).isClosed()) {
                    continue;
                }
                clients.get(i).getOutputStream().write(b);
            }

            b = fromClient.read();
        }

        socket.close();

    }

}
