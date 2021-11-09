
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server implements Runnable {
  Socket session;
  BufferedReader reader;
  PrintWriter writer;

  Server(Socket session) {
    this.session = session;
    try {
      reader = new BufferedReader(new InputStreamReader(session.getInputStream()));
      writer = new PrintWriter(session.getOutputStream());
    } catch(Exception e) {
      System.out.println("IOException: \n" + e);
    }
  }

  public static void main(String[] args) {
    try {
      ServerSocket socket = new ServerSocket(8080);
      Socket session;

      while(true) {
        System.out.println("Awaiting client..");
        session = socket.accept();
        System.out.println("Got client! Assigning to thread");
        new Thread(new Server(session)).start();
      }
    } catch(Exception e) {
      System.out.println("Exceptions: \n" + e);
    }
  }

  @Override
  public void run() {
    System.out.println("Thread started, handling client");

    try {
      serveClient();
    } catch(Exception e) {
      System.out.println("Error serving client: \n" + e);
    }
  }

  private void serveClient() throws Exception {
    Guess game = new Guess();
    Handler handler = new Handler(reader);

    String temp = reader.readLine();

    String response = handler.generateResponse(temp);

    writer.print(response);
    writer.flush();

    Thread.sleep(100);
    String line = reader.readLine();
    while(line != null) {
      Thread.sleep(10);
      System.out.println("Found line: " + line);
      line = reader.readLine();
    }
    line = reader.readLine();
    System.out.println("After ctlr: " + line);

    System.out.println("sending: \n" + response);


  }





}
