
import java.time.LocalDateTime;
import java.io.BufferedReader;

class Handler {

  private BufferedReader reader;

  private static final String HTTP_OK = "HTTP/1.1 200 OK";
  private static final String HTTP_NOT_FOUND = "HTTP/1.1 404 File Not Found";
  private static final String HTTP_NOT_FOUND_RESPONSE = "404 not found";
  private static final String HTTP_NOT_IMPL = "HTTP/1.1 501 Not Implemented";
  private static final String HTTP_NOT_IMPL_RESPONSE = "501 not implemented";

  private static final String GAME_LANDING_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>Welcome to number guessing game!<br>I have number between 1 and 100.<br>Whats your guess:<form method='POST'><input type='text' name='guess'><input type='submit' value='Submit'></form></body></html>";
  private static final String GAME_ANSWER_IS_LARGER_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>Number is larger!<br>Whats your guess:<form method='POST'><input type='text' name='guess'><input type='submit' value='Submit'></form></body></html>";
  private static final String GAME_ANSWER_IS_LOWER_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>Number is smaller!<br>Whats your guess:<form method='POST'><input type='text' name='guess'><input type='submit' value='Submit'></form></body></html>";
  private static final String GAME_CORRECT_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>You got it!<br>Play again?<form method='POST'><input type='submit' name='play' value='Play Again!'></form></body></html>";


  Handler(BufferedReader reader) {
    this.reader = reader;
  }

  public String generateResponse(Guess game, String request) throws Exception {
    System.out.println(request);

    String[] requestArr = request.split(" ");

    switch (requestArr[0]) {
      case "GET":
        return getRequest(requestArr[1], requestArr[2]);

      case "POST":
        return postRequest(requestArr[1], requestArr[2], game);

      default:
        return otherRequest();
    }
  }


  String getRequest(String resource, String version) {
    if(resource.equals("/") || resource.equals("/index.html")) {
      return HTTP_OK + "\nDate: " + LocalDateTime.now() + "\nContent-length: " + GAME_LANDING_HTML.length() + "\nContent-type: text/html\r\n\r\n" + GAME_LANDING_HTML;
    } else {
      return HTTP_NOT_FOUND + "\r\nDate: " + LocalDateTime.now() + "\r\nContent-Length: " +
             HTTP_NOT_FOUND_RESPONSE.length() + "\r\nContent-type: text/html\r\n\r\n" + HTTP_NOT_FOUND_RESPONSE;
    }
  }

  String postRequest(String resource, String version, Guess game) throws Exception {
    String line = reader.readLine();
    int contentLenth = 0;

    while(!line.equals("")) {
      System.out.println("Found line: " + line);
      if(line.toLowerCase().startsWith("content-length")) {
        contentLenth = Integer.parseInt(line.split(" ")[1]);
        System.out.println("Len identified: " + contentLenth);

      }
      line = reader.readLine();
    }

    if(contentLenth == 0) {
      System.out.println("no length in post");
    }

    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < contentLenth; i++) {
      sb.append((char)reader.read());
    }
    System.out.println("Final line: " + sb.toString());

    if(sb.toString().startsWith("play")) {
      return HTTP_OK + "\nDate: " + LocalDateTime.now() + "\nContent-length: " + GAME_LANDING_HTML.length() + "\nContent-type: text/html\r\n\r\n" + GAME_LANDING_HTML;
    }

    int res = game.guess(Integer.parseInt(sb.toString().split("=")[1]));

    String resString = "game error";

    switch(res) {
      case 0:
        resString = GAME_CORRECT_HTML;
        break;
      case 1:
        resString = GAME_ANSWER_IS_LARGER_HTML;
        break;
      case 2:
        resString = GAME_ANSWER_IS_LOWER_HTML;
        break;
      default:
        System.out.println("should not get here");
    }
    return HTTP_OK + "\nDate: " + LocalDateTime.now() + "\nContent-length: " + resString.length() + "\nContent-type: text/html\r\n\r\n" + resString;
  }

  String otherRequest() {
    return HTTP_NOT_IMPL + "\r\nDate: " + LocalDateTime.now() + "\r\nContent-Length: " +
           HTTP_NOT_IMPL_RESPONSE.length() + "\r\nContent-type: text/html\r\n\r\n" + HTTP_NOT_IMPL_RESPONSE;

  }



}
