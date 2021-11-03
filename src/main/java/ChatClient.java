
import java.net.Socket;
import java.util.Scanner;
import java.io.OutputStream;

class ChatClient {

    public static void main(String[] args) {
        System.out.println("Client starting..");

        try {
            doStuff();
        } catch (Exception e) {
            System.out.println("Something wrong 2 \n" + e);
        }
        System.out.println("Client stopping..");

    }

    private static void doStuff() throws Exception {
        Socket socket = new Socket("localhost", 1337);
        OutputStream toServer = socket.getOutputStream();
        Scanner in = new Scanner(System.in);
        
        System.out.println("Chat running. Start messaging");
    
        while(true) {
            
            System.out.print("you: ");
            String message = in.nextLine();
            toServer.write(message.getBytes());
            toServer.write("\n".getBytes());

            
        }
    }

}
