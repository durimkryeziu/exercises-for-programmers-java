package dev.delivercraft.retirement;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.time.Year;
import java.util.Objects;

class RetirementCalculator {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    private final YearProvider yearProvider;

    RetirementCalculator(LineReader lineReader, LineWriter lineWriter, YearProvider yearProvider) {
        Objects.requireNonNull(lineReader, "lineReader must not be null");
        Objects.requireNonNull(lineWriter, "lineWriter must not be null");
        Objects.requireNonNull(yearProvider, "yearProvider must not be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
        this.yearProvider = yearProvider;
    }

    void printYearsLeft() {
        int currentAge = toInt(promptForInput("What is your current age? "));
        int retirementAge = toInt(promptForInput("At what age would you like to retire? "));
        int yearsLeft = retirementAge - currentAge;
        if (yearsLeft <= 0) {
            this.lineWriter.writeLine("You can already retire.");
        } else {
            this.lineWriter.writeLine("You have %d years left until you can retire.".formatted(yearsLeft));
        }
        Year currentYear = this.yearProvider.currentYear();
        this.lineWriter.writeLine("It's %s, so you can retire in %s.".formatted(currentYear,
                currentYear.plusYears(yearsLeft)));
    }

    private String promptForInput(String prompt) {
        this.lineWriter.write(prompt);
        String input = this.lineReader.readLine();
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
