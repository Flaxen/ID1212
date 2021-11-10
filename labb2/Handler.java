
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;

class Handler {

  private static final String HTTP_OK = "HTTP/1.1 200 OK";
  private static final String HTTP_NOT_FOUND = "HTTP/1.1 404 File Not Found";
  private static final String HTTP_NOT_FOUND_RESPONSE = "404 not found";
  private static final String HTTP_NOT_IMPL = "HTTP/1.1 501 Not Implemented";
  private static final String HTTP_NOT_IMPL_RESPONSE = "501 not implemented";

  private static final String GAME_LANDING_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>Welcome to number guessing game!<br>I have number between 1 and 100.<br>Whats your guess:<form method='POST'><input type='text' name='guess'><input type='submit' value='Submit'></form></body></html>";
  private static final String GAME_ANSWER_IS_LARGER_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>Number is larger!<br>Whats your guess:<form method='POST'><input type='text' name='guess'><input type='submit' value='Submit'></form></body></html>";
  private static final String GAME_ANSWER_IS_LOWER_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>Number is smaller!<br>Whats your guess:<form method='POST'><input type='text' name='guess'><input type='submit' value='Submit'></form></body></html>";
  private static final String GAME_CORRECT_HTML = "<!DOCTYPE html><html lang='en' dir='ltr'><head><meta charset='utf-8'><title></title></head><body>You got it!<br>Play again?<form method='POST'><input type='submit' name='play' value='Play Again!'></form></body></html>";

  private BufferedReader reader;
  private ArrayList<Guess> games =  new ArrayList<Guess>();
  private int gameCounter = 0;

  private Random rand = new Random(System.currentTimeMillis());

  public void setReader(BufferedReader reader) {
    this.reader = reader;
  }

  private String getFullRequest() throws Exception {

    StringBuilder sb = new StringBuilder();

    // get header
    String temp = reader.readLine();
    while(!temp.equals("")) {
      // System.out.println(temp);
      sb.append(temp + "\n");
      temp = reader.readLine();
    }

    int contentLength;
    String contentLengthTemp = getHeaderPiece(sb.toString(), "content-length");
    if(contentLengthTemp == null) {
      contentLength = 0;
    } else {
      contentLength = Integer.parseInt(contentLengthTemp.split(" ")[1]);
      sb.append("\n");
    }

    for(int i = 0; i < contentLength; i++) {
      sb.append((char)reader.read());
    }

    return sb.toString();
  }

  private String getHeaderPiece(String headers, String headerName) {
    headerName = headerName.toLowerCase();
    String[] headerArr = headers.split("\n");

    for(int i = 0; i < headerArr.length; i++) {
      // System.out.println("ID " + i + "header: " + headerArr[i]);
      if(headerArr[i].toLowerCase().startsWith(headerName)) {
        return headerArr[i];
      }
    }
    return null;
  }

  // returns sessionId cookie or -1 if none
  private int hasCookie(String fullRequest) {

    String cookieHeader = getHeaderPiece(fullRequest, "cookie");

    if(cookieHeader == null) {
      return -1;
    }

    return Integer.parseInt(cookieHeader.split("=")[1]);
  }

  private String cookieCheck(String headers) {
    String cookieResultPart;

    if(hasCookie(headers) == -1) {
      cookieResultPart = "\nSet-Cookie: sessionId=" + gameCounter;
      Guess newGame = new Guess(gameCounter++);
      newGame.setAnswer(rand.nextInt(100) + 1);
      games.add(newGame);
    } else {
      cookieResultPart = "";
    }

    return cookieResultPart;
  }

  private int getGameIndex(int cookie) {

    for(int i = 0; i < games.size(); i++) {
      if(games.get(i).getCookieId() == cookie) {
        return i;
      }
    }
    return -1;
  }

  public String generateResponse() throws Exception {
    String requestLine = reader.readLine();
    System.out.println(requestLine);

    String[] requestArr = requestLine.split(" ");

    switch (requestArr[0]) {
      case "GET":
        return getRequest(requestArr[1], requestArr[2]);

      case "POST":
        return postRequest(requestArr[1], requestArr[2]);
      //
      // default:
      //   return otherRequest();
    }
    return "";
  }


  String getRequest(String resource, String version) throws Exception {
    String headers = getFullRequest();
    // System.out.println(headers);

    String cookieResultPart = cookieCheck(headers);

    if(resource.equals("/") || resource.equals("/index.html")) {
      return HTTP_OK + "\nDate: " + LocalDateTime.now() + cookieResultPart + "\nContent-length: " + GAME_LANDING_HTML.length() + "\nContent-type: text/html\r\n\r\n" + GAME_LANDING_HTML;
    } else {
      return HTTP_NOT_FOUND + "\r\nDate: " + LocalDateTime.now() + "\r\nContent-Length: " +
             HTTP_NOT_FOUND_RESPONSE.length() + "\r\nContent-type: text/html\r\n\r\n" + HTTP_NOT_FOUND_RESPONSE;
    }
  }

  String postRequest(String resource, String version) throws Exception {

    String rest = getFullRequest();
    // System.out.println(rest);

    if(getHeaderPiece(rest, "content-length") == null) {
      return HTTP_OK + "\nDate: " + LocalDateTime.now() + "\nContent-length: " + "error empty post".length() + "\nContent-type: text/html\r\n\r\n" + "error empty post";
    }

    String cookieHeader = getHeaderPiece(rest, "cookie");
    if(cookieHeader == null) {
      return HTTP_OK + "\nDate: " + LocalDateTime.now() + "\nContent-length: " + "error no cookie".length() + "\nContent-type: text/html\r\n\r\n" + "error no cookie";
    }

    String[] requestRows = rest.split("\n");
    String postBody = requestRows[requestRows.length-1];

    if(postBody.startsWith("play")) {

      int cookie = Integer.parseInt(cookieHeader.split("=")[1]);
      int gameIndex = getGameIndex(cookie);
      games.remove(gameIndex);

      Guess newGame = new Guess(gameCounter);
      newGame.setAnswer(rand.nextInt(100) + 1);
      games.add(newGame);
      String cookieResultPart = "\nSet-Cookie: sessionId=" + gameCounter++;

      return HTTP_OK + "\nDate: " + LocalDateTime.now() + cookieResultPart + "\nContent-length: " + GAME_LANDING_HTML.length() + "\nContent-type: text/html\r\n\r\n" + GAME_LANDING_HTML;
    }

    int cookie = Integer.parseInt(cookieHeader.split("=")[1]);
    int gameIndex = getGameIndex(cookie);

    Guess currentGame = games.get(gameIndex);

    for(int i = 0; i < games.size(); i++) {
      System.out.println("==== answer " + i + " " + games.get(i).getAnswer());
    }

    int res = currentGame.guess(Integer.parseInt(postBody.split("=")[1]));

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
    // return null;
  }
  //
  // String otherRequest() {
  //   return HTTP_NOT_IMPL + "\r\nDate: " + LocalDateTime.now() + "\r\nContent-Length: " +
  //          HTTP_NOT_IMPL_RESPONSE.length() + "\r\nContent-type: text/html\r\n\r\n" + HTTP_NOT_IMPL_RESPONSE;
  //
  // }



}
