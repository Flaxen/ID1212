
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server {

  public static void main(String[] args) {
    Socket session;
    BufferedReader reader;
    PrintWriter writer;

    try {
      ServerSocket socket = new ServerSocket(8080);
      Handler handler = new Handler();

      while(true) {

        System.out.println("Awaiting message..");
        session = socket.accept();

        reader = new BufferedReader(new InputStreamReader(session.getInputStream()));
        handler.setReader(reader);
        writer = new PrintWriter(session.getOutputStream());

        System.out.println("Got message!");

        String response = handler.generateResponse();

        writer.print(response);
        writer.flush();
        System.out.println("sending: \n" + response);
      }
    } catch(Exception e) {
      System.out.println("Exceptions: \n" + e);
    }
  }
}
