
import java.net.Socket;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.InputStream;

class ChatClient {

    public static void main(String[] args) {
        System.out.println("Client starting..");
        Socket socket = null;
        try {
            socket = new Socket("localhost", 1337);
        } catch (Exception e) {
            System.out.println("Something wrong:\n" + e);
        }

        ChatClientMonitor ccm = new ChatClientMonitor(socket);
        Thread thread = new Thread(ccm);
        thread.start();

        ChatClientMessageReceiver ccmr = new ChatClientMessageReceiver(socket);
        thread = new Thread(ccmr);
        thread.start();

        try {
            doStuff(socket);
        } catch (Exception e) {
            System.out.println("Seems your messages aren't going through");
        }
        System.out.println("Client shutting down..");
        //while(true);
    }

    private static void doStuff(Socket socket) throws Exception {
        OutputStream toServer = socket.getOutputStream();
        Scanner in = new Scanner(System.in);

        System.out.println("Chat running. Start messaging");

        while (true) {

            String message = in.nextLine();
            toServer.write(message.getBytes());
            toServer.write("\n".getBytes());

        }
    }
}

class ChatClientMessageReceiver implements Runnable {

    Socket socket;

    ChatClientMessageReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            messageReceiver();
        } catch (Exception e) {
            System.out.println("Something wrong 3:\n" + e);
        }
    }

    private void messageReceiver() throws Exception {
        InputStream fromServer = socket.getInputStream();

        int b = fromServer.read();
        while (b != -1) {

            System.out.print((char) b);

            b = fromServer.read();
        }
    }
}

class ChatClientMonitor implements Runnable {

    Socket socket;

    ChatClientMonitor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            OutputStream toServer = socket.getOutputStream();
            while (true) {
                toServer.write(6);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Server connection lost\n");
        }
    }
}
