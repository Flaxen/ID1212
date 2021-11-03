
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;

public class ChatServer {

    public static void main(String args[]) {
        // TODO code application logic here
        System.out.println("Server starting..\n");
        try {
            doStuff();
        } catch (Exception e) {
            System.out.println("Something wrong 1 \n" + e);
        }
        System.out.println("Server shutting down..");


    }

    private static void doStuff() throws Exception {

        ServerSocket socket = new ServerSocket(1337);
        Socket session;

        while (true) {
            session = socket.accept();

            InputStream fromClient = session.getInputStream();

            System.out.println("Server got message:");
            int b = fromClient.read();
            while (b != -1) {
                System.out.print((char)b);
                
                b = fromClient.read();
            }
            System.out.println("\nEnd of server message");

            session.close();
        }
        //socket.close();
    }
}
