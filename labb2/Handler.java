
import java.time.LocalDateTime;
import java.io.BufferedReader;

class Handler {

  private BufferedReader reader;

  private static final String HTTP_OK = "HTTP/1.1 200 OK";
  private static final String HTTP_NOT_FOUND = "HTTP/1.1 404 File Not Found";
  private static final String HTTP_NOT_FOUND_RESPONSE = "404 not found";
  private static final String HTTP_NOT_IMPL = "HTTP/1.1 501 Not Implemented";
  private static final String HTTP_NOT_IMPL_RESPONSE = "501 not implemented";

  private static final String GAME_LANDING_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>Welcome to number guessing game!<br>I have number between 1 and 100.<br>Whats your guess:<form method='POST'><input type='text' name='guess'><input type='submit' name='submit' value='Submit'></form></body></html>";
  // private static final String GAME_ANSWER_IS_LARGER_HTML
  // private static final String GAME_ANSWER_IS_LOWER_HTML
  // private static final String GAME_CORRECT_HTML

  Handler(BufferedReader reader) {
    this.reader = reader;
  }

  public String generateResponse(String request) throws Exception {
    System.out.println(request);

    String[] requestArr = request.split(" ");

    switch (requestArr[0]) {
      case "GET":
        return getRequest(requestArr[1], requestArr[2]);

      case "POST":
        return postRequest(requestArr[1], requestArr[2]);

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

  String postRequest(String resource, String version) throws Exception {

    return HTTP_OK + "\nDate: " + LocalDateTime.now() + "\nContent-length: " + GAME_LANDING_HTML.length() + "\nContent-type: text/html\r\n\r\n" + GAME_LANDING_HTML;
  }

  String otherRequest() {
    return HTTP_NOT_IMPL + "\r\nDate: " + LocalDateTime.now() + "\r\nContent-Length: " +
           HTTP_NOT_IMPL_RESPONSE.length() + "\r\nContent-type: text/html\r\n\r\n" + HTTP_NOT_IMPL_RESPONSE;

  }



}
