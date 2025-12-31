package dev.delivercraft.madlib;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

class MadLib {

    private final InputStream inputStream;

    private final PrintStream printStream;

    MadLib(InputStream inputStream, PrintStream printStream) {
        Objects.requireNonNull(inputStream, "inputStream cannot be null");
        Objects.requireNonNull(printStream, "printStream cannot be null");
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    void printStory() {
        try (Scanner scanner = new Scanner(inputStream)) {
            String noun = promptForInput("Enter a noun: ", scanner);
            if (noun == null) {
                return;
            }
            String verb = promptForInput("Enter a verb: ", scanner);
            if (verb == null) {
                return;
            }
            String adjective = promptForInput("Enter an adjective: ", scanner);
            if (adjective == null) {
                return;
            }
            String adverb = promptForInput("Enter an adverb: ", scanner);
            if (adverb == null) {
                return;
            }
            this.printStream.println(
                    "Do you " + verb + " your " + adjective + " " + noun + " " + adverb + "? That's hilarious!");
        }
    }

    @SuppressWarnings("PMD.SystemPrintln")
    private String promptForInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        String input = readInput(scanner);
        if (input == null || input.isBlank()) {
            this.printStream.println("Please enter something as input!");
            return null;
        }
        return input;
    }

    private String readInput(Scanner scanner) {
        return scanner.hasNext() ? scanner.nextLine() : null;
    }

}
