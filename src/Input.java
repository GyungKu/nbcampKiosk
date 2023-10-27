import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    Scanner sc = new Scanner(System.in);

    public int inputInt() throws IllegalArgumentException {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String inputStr() {
        sc.nextLine();
        return sc.nextLine();
    }
}
