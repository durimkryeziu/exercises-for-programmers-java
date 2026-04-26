package dev.delivercraft.madlib;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import java.util.Objects;

class MadLib {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    MadLib(LineReader lineReader, LineWriter lineWriter) {
        Objects.requireNonNull(lineReader, "lineReader cannot be null");
        Objects.requireNonNull(lineWriter, "lineWriter cannot be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    void printStory() {
        String noun = promptForInput("Enter a noun: ");
        if (noun == null) {
            return;
        }
        String verb = promptForInput("Enter a verb: ");
        if (verb == null) {
            return;
        }
        String adjective = promptForInput("Enter an adjective: ");
        if (adjective == null) {
            return;
        }
        String adverb = promptForInput("Enter an adverb: ");
        if (adverb == null) {
            return;
        }
        this.lineWriter.writeLine("Do you " + verb + " your " + adjective + " " + noun + " " + adverb
                + "? That's hilarious!");
    }

    private String promptForInput(String prompt) {
        this.lineWriter.write(prompt);
        String input = this.lineReader.readLine();
        if (input == null || input.isBlank()) {
            this.lineWriter.writeLine("Please enter something as input!");
            return null;
        }
        return input;
    }

}
