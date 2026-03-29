package dev.delivercraft.pizza;

import java.util.Objects;

final class PizzaParty {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    PizzaParty(LineReader lineReader, LineWriter lineWriter) {
        Objects.requireNonNull(lineReader, "lineReader must not be null");
        Objects.requireNonNull(lineWriter, "lineWriter must not be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    void displayPizzaParty() {
        int people = toPeopleCount(readRequiredInput("How many people? "));
        int pizzas = toNumber(readRequiredInput("How many pizzas do you have? "));
        int slicesPerPizza = toNumber(readRequiredInput("How many slices per pizza? "));

        int totalSlices = pizzas * slicesPerPizza;
        int piecesPerPerson = totalSlices / people;
        int leftoverPieces = totalSlices % people;

        this.lineWriter.writeLine("");
        this.lineWriter.writeLine("%d people with %d %s".formatted(people, pizzas,
                pluralize(pizzas, "pizza", "pizzas")));
        this.lineWriter.writeLine("Each person gets %d %s of pizza.".formatted(piecesPerPerson,
                pluralize(piecesPerPerson, "piece", "pieces")));
        this.lineWriter.writeLine("There %s %d %s.".formatted(verbForLeftover(leftoverPieces), leftoverPieces,
                pluralize(leftoverPieces, "leftover piece", "leftover pieces")));
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

    private static String pluralize(int value, String singular, String plural) {
        return value == 1 ? singular : plural;
    }

    private static String verbForLeftover(int leftoverPieces) {
        return leftoverPieces == 1 ? "is" : "are";
    }
}
