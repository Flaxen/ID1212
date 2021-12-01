
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.Console;
import java.io.File;
import java.util.Base64;




class Mailer {
  private static DataOutputStream dataOutStream;

  public static void main(String[] args) {

    String username = readFile("username.txt");
    String password = readFile("password.txt");

    // readMail(username, password);
    sendMail(username, password);

  }

  private static String readFile(String filename) {
    Scanner file = null;
    try {
      file = new Scanner(new File(filename));

    } catch(Exception e) {
      System.out.println("error file reader creation " + e);
    }

    return file.nextLine();
  }

  private static void readMail(String username, String password) {
    Scanner in = new Scanner(System.in);

    SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();

    HttpsURLConnection.setDefaultSSLSocketFactory(sf);
    SSLSocket socket = null;
    String host = "webmail.kth.se";

    try {

      socket = (SSLSocket)sf.createSocket(host,993);
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


      // writer.println("abcd CAPABILITY\r\n");
      writer.println("a001 LOGIN " + username + " " + password + "\r\n");
      // writer.println("A101 LIST" + " \"\" " + "*\r\n" );
      writer.println("A142 SELECT INBOX\r\n");
      writer.println("A654 FETCH 1:1 (FLAGS BODY[HEADER.FIELDS (DATE FROM)] BODY[TEXT])\r\n");

      writer.flush();


      String line = reader.readLine();
      while (line != null) {
        System.out.println(line);

        if(line.startsWith("A654 OK FETCH completed.")) {
          writer.println("A023 LOGOUT\r\n");
          writer.flush();
        } else if(line.startsWith("A023 OK LOGOUT completed.")) {
          break;
        }

        line = reader.readLine();
      }

    } catch(Exception e) {
      System.out.println("exception in readmail " + e);
    }
  }

  private static void sendMail(String username, String password) {
    Scanner in = new Scanner(System.in);

    Socket socket = null;
    String host = "smtp.kth.se";

    try {

      socket = new Socket(host,587);
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      writer.println("EHLO " + host);
      writer.flush();

      writer.println("STARTTLS");

      writer.flush();

      String line = reader.readLine();
      while (line != null) {
        System.out.println(line);
        if(line.startsWith("220 2.0.0 Ready to start TLS")) {
          break;
        }

        line = reader.readLine();
      }

      SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();

      SSLSocket sslsocket = (SSLSocket)sf.createSocket(socket, socket.getInetAddress().getHostAddress(), socket.getPort(), true);
      PrintWriter writer2 = new PrintWriter(sslsocket.getOutputStream(), true);
      BufferedReader reader2 = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
      writer2.println("EHLO " + host);
      writer2.flush();

      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());
      System.out.println(reader2.readLine());

      Thread.sleep(1000);

      writer2.println("AUTH LOGIN");
      writer2.flush();
      System.out.println(reader2.readLine());

      writer2.println(Base64.getEncoder().encodeToString(readFile("username.txt").getBytes()));
      writer2.flush();
      System.out.println(reader2.readLine());

      writer2.println(Base64.getEncoder().encodeToString(readFile("password.txt").getBytes()));
      writer2.flush();
      System.out.println(reader2.readLine());

      writer2.println("MAIL FROM:<alecarls@kth.se>");
      writer2.flush();
      System.out.println(reader2.readLine());
      Thread.sleep(2000);

      writer2.println("RCPT TO:<alecarls@kth.se>");
      writer2.flush();
      System.out.println(reader2.readLine());

      Thread.sleep(3000);

      writer2.println("DATA");
      writer2.flush();
      System.out.println(reader2.readLine());

      Thread.sleep(1000);


      writer2.println("Hej lol\n Skickat från min java");
      Thread.sleep(1000);

      writer2.println(".");


      line = reader2.readLine();
      while (line != null) {
        System.out.println(line);
        if(line.startsWith("250 2.0.0 Ok: queued as")) {
          System.out.println("done");
          break;
        }
        line = reader2.readLine();
      }

    } catch(Exception e) {
      System.out.println("exception in sendMail 2 " + e);
    }

  }
}


















//
