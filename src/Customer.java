import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {

    Scanner sc = new Scanner(System.in);

    public int select() {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
