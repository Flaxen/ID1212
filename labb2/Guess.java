
import java.util.Scanner;

public class Guess {
  private int answer;

  private int cookieId;
  private int latestState;
  private int guesses = 0;

  Guess(int cookieId) {
    this.cookieId = cookieId;
    this.answer = answer;
    // System.out.println("================== new random is: " + answer);

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

  // public static void main(String[] args) {
  //     int guess = 0;                              // 0 is not in answer range. works as init
  //
  //     Scanner in = new Scanner(System.in);
  //     System.out.println("Guess game!");
  //
  //     while (true) {
  //         System.out.print("Guess plz: ");
  //         guess = in.nextInt();
  //
  //         if (guess == answer) {
  //             System.out.println("You got it!");
  //             break;
  //         } else if (guess < answer) {
  //             System.out.println("Answer i larger!");
  //
  //         } else {
  //             System.out.println("Answer is smaller!");
  //
  //         }
  //     }
  // }

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
