package my_calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new File("input.txt"));
        Calculator calculator = new Calculator(in.nextLine());
        calculator.calculate();

        System.out.println("Результат вычислений равен: " + calculator.getResult());
    }
}
