import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Math.*;

public class Pra1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        ArrayList<Integer> input_list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            input_list.add(in.nextInt());
        }
        System.out.println("Output all even numbers.");
        input_list.stream().filter(i -> i % 2 == 0).forEach(i -> System.out.printf("%d ", i));
        System.out.println();
        System.out.println("Output all odd numbers.");
        input_list.stream().filter(i -> i % 2 == 1).forEach(i -> System.out.printf("%d ", i));
        System.out.println();
        System.out.println("Output all prime numbers.");
        input_list.stream().filter(i -> {
            for (int t = 2; t <= (int) ceil(pow(i, 0.5)); t ++) {
                if (i % t == 0) return false;
            }
            return true;
        }).forEach(i -> System.out.printf("%d ", i));
        System.out.println();
        System.out.println("Output all prime numbers that bigger than 5.");
        input_list.stream().filter(i -> {
            if (i <= 5) return false;
            for (int t = 2; t <= (int) ceil(pow(i, 0.5)); t ++) {
                if (i % t == 0) return false;
            }
            return true;
        }).forEach(i -> System.out.printf("%d ", i));
        System.out.println();
    }
}
