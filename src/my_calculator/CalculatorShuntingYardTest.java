package my_calculator;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CalculatorShuntingYardTest {

    @Test
    public void testCalculate() throws FileNotFoundException {

        String expression = "pow(2,10) + 2^10 + 5";

        Calculator calculator = new Calculator(expression);
        calculator.calculate();

        String result = Double.toString(calculator.getResult());

        assertEquals("2053.0", result);


    }


}
