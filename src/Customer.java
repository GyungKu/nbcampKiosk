import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {

    Scanner sc = new Scanner(System.in);

    public int select() {
        try {
            int input = sc.nextInt();
            return input;
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
