package dev.delivercraft.math.console;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.util.Objects;

class SimpleMath {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    SimpleMath(LineReader lineReader, LineWriter lineWriter) {
        Objects.requireNonNull(lineReader, "lineReader must not be null");
        Objects.requireNonNull(lineWriter, "lineWriter must not be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    void printOutput() {
        String firstInput = promptForInput("What is the first number? ");
        PositiveInteger firstNumber = PositiveInteger.of(firstInput);
        String secondInput = promptForInput("What is the second number? ");
        PositiveInteger secondNumber = PositiveInteger.of(secondInput);
        this.lineWriter.writeLine(formattedOutput(firstNumber, secondNumber));
    }

    private String promptForInput(String prompt) {
        this.lineWriter.write(prompt);
        String input = this.lineReader.readLine();
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
