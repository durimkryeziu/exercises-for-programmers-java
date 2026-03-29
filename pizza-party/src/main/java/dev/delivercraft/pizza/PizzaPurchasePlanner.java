package dev.delivercraft.pizza;

import java.util.Objects;

final class PizzaPurchasePlanner {

    private static final int SLICES_PER_PIZZA = 8;

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    PizzaPurchasePlanner(LineReader lineReader, LineWriter lineWriter) {
        Objects.requireNonNull(lineReader, "lineReader must not be null");
        Objects.requireNonNull(lineWriter, "lineWriter must not be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    void displayPizzaPurchasePlan() {
        int people = toPeopleCount(readRequiredInput("How many people? "));
        int piecesPerPerson = toNumber(readRequiredInput("How many pieces does each person want? "));

        int requiredPieces = people * piecesPerPerson;
        int requiredPizzas = divideRoundingUp(requiredPieces, SLICES_PER_PIZZA);

        this.lineWriter.writeLine("");
        this.lineWriter.writeLine("You will need to purchase %d %s.".formatted(
                requiredPizzas, pluralize(requiredPizzas, "pizza", "pizzas")));
    }

    private String readRequiredInput(String prompt) {
        this.lineWriter.write(prompt);
        String input = this.lineReader.readLine();
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input must not be empty!");
        }
        return input;
    }

    private static int toPeopleCount(String value) {
        int people = toNumber(value);
        if (people == 0) {
            throw new IllegalArgumentException("Number of people must be greater than zero!");
        }
        return people;
    }

    private static int toNumber(String value) {
        try {
            int number = Integer.parseInt(value);
            if (number < 0) {
                throw new IllegalArgumentException("Please enter a positive number! Input: %d".formatted(number));
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number! Input: %s".formatted(value), e);
        }
    }

    private static int divideRoundingUp(int value, int divisor) {
        return (value + divisor - 1) / divisor;
    }

    private static String pluralize(int value, String singular, String plural) {
        return value == 1 ? singular : plural;
    }
}
