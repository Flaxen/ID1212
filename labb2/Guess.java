
import java.util.Scanner;
import java.util.Random;

public class Guess {

    public static void main(String[] args) {

        int answer = new Random().nextInt(100) + 1; // range [0, 100). this row gives [1, 101) = [1, 100]
        int guess = 0;                              // 0 is not in answer range. works as init

        Scanner in = new Scanner(System.in);

        System.out.println("Guess game!");

        while (true) {
            System.out.print("Guess plz: ");
            guess = in.nextInt();

            if (guess == answer) {
                System.out.println("You got it!");
                break;
            } else if (guess < answer) {
                System.out.println("Answer i larger!");

            } else {
                System.out.println("Answer is smaller!");

            }
        }

    }
}
