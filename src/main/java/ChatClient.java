
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
        } catch(Exception e) {
            System.out.println("Something wrong:\n" + e);
        }

        ChatClientMessageReceiver ccmr = new ChatClientMessageReceiver(socket);
        Thread thread = new Thread(ccmr);
        thread.start();

        try {
            doStuff(socket);
        } catch (Exception e) {
            System.out.println("Something wrong 2 \n" + e);
        }
        System.out.println("Client stopping..");

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
        while(b != -1) {
            
            System.out.print((char)b);
            
            b = fromServer.read();
        }
    }
}
