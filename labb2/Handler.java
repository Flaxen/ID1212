
import java.time.LocalDateTime;

class Handler {

  private static final String HTTP_OK = "HTTP/1.1 200 OK";
  private static final String HTTP_NOT_FOUND = "HTTP/1.1 404 File Not Found";
  private static final String HTTP_NOT_FOUND_REPONSE = "404 not found";
  private static final String HTTP_NOT_IMPL = "HTTP/1.1 501 Not Implemented";
  private static final String HTTP_NOT_IMPL_RESPONSE = "501 not implemented";

  private static final String GAME_LANDING_HTML
  private static final String GAME_ANSWER_IS_LARGER_HTML
  private static final String GAME_ANSWER_IS_LOWER_HTML
  private static final String GAME_CORRECT_HTML


  public String generateResponse(String request) {
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
      return HTTP_OK + "\nDate: " + LocalDateTime.now() + "\nContent-length: " + "\nContent-type: text/hmtl";
    } else {
      return HTTP_NOT_FOUND + "\r\nDate: " + LocalDateTime.now() + "\r\nContent-Length: " +
             HTTP_NOT_FOUND_REPONSE.length() + "\r\nContent-type: text/hmtl\r\n\r\n" + HTTP_NOT_FOUND_REPONSE;
    }
  }

  String postRequest(String resource, String version) {
    return "";
  }

  String otherRequest() {
    return HTTP_NOT_IMPL + "\r\nDate: " + LocalDateTime.now() + "\r\nContent-Length: " +
           HTTP_NOT_IMPL_RESPONSE.length() + "\r\nContent-type: text/hmtl\r\n\r\n" + HTTP_NOT_IMPL_REPONSE;

  }



}
