package com.craftsmanshipinsoftware.math;

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
      int firstNumber = validatedInput(firstInput);
      String secondInput = promptForInput("What is the second number? ", scanner);
      int secondNumber = validatedInput(secondInput);
      if (secondNumber == 0) {
        throw new IllegalArgumentException("Cannot divide by zero!");
      }
      this.printStream.println(output(firstNumber, secondNumber));
    }
  }

  @SuppressWarnings("PMD.SystemPrintln")
  private String promptForInput(String prompt, Scanner scanner) {
    System.out.print(prompt);
    String input = readInput(scanner);
    if (input == null || input.isBlank()) {
      throw new IllegalArgumentException("Input must not be empty!");
    }
    return input;
  }

  private String readInput(Scanner scanner) {
    return scanner.hasNext() ? scanner.nextLine() : null;
  }

  private static int validatedInput(String input) {
    try {
      int number = Integer.parseInt(input);
      if (number < 0) {
        throw new IllegalArgumentException("Please enter a positive number! Input: " + number);
      }
      return number;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Please enter a valid number! Input: " + input, e);
    }
  }

  private static String output(int firstNumber, int secondNumber) {
    return String.format("""
            %d + %d = %d
            %d - %d = %d
            %d * %d = %d
            %d / %d = %d""",
        firstNumber, secondNumber, add(firstNumber, secondNumber),
        firstNumber, secondNumber, subtract(firstNumber, secondNumber),
        firstNumber, secondNumber, multiply(firstNumber, secondNumber),
        firstNumber, secondNumber, divide(firstNumber, secondNumber));
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
    return firstNumber / secondNumber;
  }
}
