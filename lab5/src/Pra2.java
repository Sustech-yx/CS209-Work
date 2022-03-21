import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Pra2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        ArrayList<Integer> input_list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            input_list.add(in.nextInt());
        }
        // Binary Operator
        BinaryOperator<Integer> op1 = (a, b)->(a+b*b);
        System.out.println(input_list.stream().reduce(0, op1));
        // Function
        Function<Integer, Integer> op2 = i->i*i;
        System.out.println(input_list.stream().map(op2).reduce(0, Integer::sum));
        // Unary Operator
        UnaryOperator<Integer> op3 = i->i*i;
        input_list.replaceAll(op3);
        System.out.println(input_list.stream().reduce(0, Integer::sum));
    }
}
