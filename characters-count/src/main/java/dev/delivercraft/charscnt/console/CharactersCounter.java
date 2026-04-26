package dev.delivercraft.charscnt.console;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.util.Objects;

public final class CharactersCounter {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    public CharactersCounter(LineReader lineReader, LineWriter lineWriter) {
        Objects.requireNonNull(lineReader, "lineReader must not be null");
        Objects.requireNonNull(lineWriter, "lineWriter must not be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    public void displayCharactersCount() {
        this.lineWriter.write("What is the input string? ");
        String input = this.lineReader.readLine();
        if (input == null || input.isBlank()) {
            this.lineWriter.writeLine("Please enter something as input!");
            return;
        }
        this.lineWriter.writeLine("%s has %d characters.".formatted(input, input.length()));
    }

}
