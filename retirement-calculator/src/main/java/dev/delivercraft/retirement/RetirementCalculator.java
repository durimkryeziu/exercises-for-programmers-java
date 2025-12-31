package dev.delivercraft.retirement;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.Objects;
import java.util.Scanner;

class RetirementCalculator {

    private final InputStream inputStream;

    private final PrintStream printStream;

    private final YearProvider yearProvider;

    RetirementCalculator(InputStream inputStream, PrintStream printStream, YearProvider yearProvider) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        Objects.requireNonNull(printStream, "printStream must not be null");
        Objects.requireNonNull(yearProvider, "yearProvider must not be null");
        this.inputStream = inputStream;
        this.printStream = printStream;
        this.yearProvider = yearProvider;
    }

    void printYearsLeft() {
        try (Scanner scanner = new Scanner(inputStream)) {
            int currentAge = toInt(promptForInput("What is your current age? ", scanner));
            int retirementAge = toInt(promptForInput("At what age would you like to retire? ", scanner));
            int yearsLeft = retirementAge - currentAge;
            if (yearsLeft <= 0) {
                this.printStream.println("You can already retire.");
            } else {
                this.printStream.printf("You have %d years left until you can retire.%n", yearsLeft);
            }
            Year currentYear = this.yearProvider.currentYear();
            this.printStream.printf("It's %s, so you can retire in %s.%n", currentYear,
                    currentYear.plusYears(yearsLeft));
        }
    }

    private String promptForInput(String prompt, Scanner scanner) {
        this.printStream.print(prompt);
        String input = scanner.hasNext() ? scanner.nextLine() : null;
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input must not be empty!");
        }
        return input;
    }

    private int toInt(String value) {
        try {
            int number = Integer.parseInt(value);
            if (number < 0) {
                throw new IllegalArgumentException("Please enter a positive number! Input: %s".formatted(value));
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number! Input: %s".formatted(value), e);
        }
    }
}
