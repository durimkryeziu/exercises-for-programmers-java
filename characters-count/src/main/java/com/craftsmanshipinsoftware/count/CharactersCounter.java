package com.craftsmanshipinsoftware.count;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

public class CharactersCounter {

  private final InputStream inputStream;
  private final PrintStream printStream;

  public CharactersCounter(InputStream inputStream, PrintStream printStream) {
    Objects.requireNonNull(inputStream, "inputStream must not be null");
    Objects.requireNonNull(printStream, "printStream must not be null");
    this.inputStream = inputStream;
    this.printStream = printStream;
  }

  public void displayCharactersCount() {
    askForInput();
    String input = readInput();
    if (input == null || input.isBlank()) {
      this.printStream.println("Please enter something as input!");
      return;
    }
    this.printStream.printf("%s has %d characters.%n", input, input.length());
  }

  private void askForInput() {
    System.out.print("What is the input string? ");
  }

  private String readInput() {
    try (Scanner scanner = new Scanner(this.inputStream)) {
      return scanner.hasNext() ? scanner.nextLine() : null;
    }
  }

}
