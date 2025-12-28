package com.craftsmanshipinsoftware.math.console;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

class SimpleMath {

    private final PrintStream printStream;

    private final InputStream inputStream;

    SimpleMath(InputStream inputStream, PrintStream printStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        Objects.requireNonNull(printStream, "printStream must not be null");
        this.printStream = printStream;
        this.inputStream = inputStream;
    }

    void printOutput() {
        try (Scanner scanner = new Scanner(inputStream)) {
            String firstInput = promptForInput("What is the first number? ", scanner);
            PositiveInteger firstNumber = PositiveInteger.of(firstInput);
            String secondInput = promptForInput("What is the second number? ", scanner);
            PositiveInteger secondNumber = PositiveInteger.of(secondInput);
            this.printStream.println(formattedOutput(firstNumber, secondNumber));
        }
    }

    @SuppressWarnings("PMD.SystemPrintln")
    private String promptForInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        String input = scanner.hasNext() ? scanner.nextLine() : null;
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input must not be empty!");
        }
        return input;
    }

    private static String formattedOutput(PositiveInteger firstNumber, PositiveInteger secondNumber) {
        return """
                %s + %s = %d
                %s - %s = %d
                %s * %s = %d
                %s / %s = %d""".formatted(
                firstNumber, secondNumber, add(firstNumber.intValue(), secondNumber.intValue()),
                firstNumber, secondNumber, subtract(firstNumber.intValue(), secondNumber.intValue()),
                firstNumber, secondNumber, multiply(firstNumber.intValue(), secondNumber.intValue()),
                firstNumber, secondNumber, divide(firstNumber.intValue(), secondNumber.intValue())
        );
    }

    private static int add(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

    private static int subtract(int firstNumber, int secondNumber) {
        return firstNumber - secondNumber;
    }

    private static int multiply(int firstNumber, int secondNumber) {
        return firstNumber * secondNumber;
    }

    private static int divide(int firstNumber, int secondNumber) {
        if (secondNumber == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return firstNumber / secondNumber;
    }
}
