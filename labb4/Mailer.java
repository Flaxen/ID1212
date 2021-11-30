
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.Console;


class Mailer {
  private static DataOutputStream dataOutStream;

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();

    HttpsURLConnection.setDefaultSSLSocketFactory(sf);
    SSLSocket socket = null;
    String host = "webmail.kth.se";

    try {

      socket = (SSLSocket)sf.createSocket(host,993);
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


      writer.println("abcd CAPABILITY\r\n");
      writer.println("a001 LOGIN alecarls login.kth.se/password/change!A\r\n");
      Thread.sleep(1000);
      writer.println("A101 LIST" + " \"\" " + "*\r\n");
      Thread.sleep(1000);
      writer.println("A142 SELECT INBOX\r\n");

      writer.flush();


      String line = reader.readLine();
      while (line != null) {
        System.out.println("SERVER: "+ line);
        line = reader.readLine();
      }


    // writer.println(A023 LOGOUT);
    // lägg i if för sista output

    } catch(Exception e) {
      System.out.println("ereoeooeeo 1 " + e);
    }



  }


}
