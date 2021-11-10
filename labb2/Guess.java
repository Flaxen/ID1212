
import java.util.Scanner;

public class Guess {
  private int answer;

  private int cookieId;
  private int latestState;
  private int guesses = 0;

  Guess(int cookieId) {
    this.cookieId = cookieId;
    this.answer = answer;

  }

  public void setAnswer(int answer) {
    this.answer = answer;
  }

  public int getGuesses() {
    return guesses;
  }

  public int getAnswer() {
    return answer;
  }

  public int getCookieId() {
    return cookieId;
  }

    // return meanings:
    // 0 = correct, 1 = answer is larger, 2 = answer is smaller
  public int guess(int guess) {
    guesses++;

    if(guess == answer) {
      latestState = 0;
    } else if(guess < answer) {
      latestState = 1;
    } else {
      latestState = 2;
    }

    return latestState;
  }
}
