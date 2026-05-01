package dev.delivercraft.prntqts;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.util.Objects;

class QuotesPrinter {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    QuotesPrinter(LineReader lineReader, LineWriter lineWriter) {
        Objects.requireNonNull(lineReader, "lineReader must not be null");
        Objects.requireNonNull(lineWriter, "lineWriter must not be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    void displayQuote() {
        askForQuote();
        String quote = readInput();
        askForAuthor();
        String author = readInput();
        if (quote == null || quote.isBlank()) {
            this.lineWriter.writeLine("Please enter the quote!");
        } else if (author == null || author.isBlank()) {
            this.lineWriter.writeLine("Please enter the author!");
        } else {
            this.lineWriter.writeLine("%s says, \"%s\"".formatted(author, quote));
        }
    }

    private void askForQuote() {
        this.lineWriter.write("What is the quote? ");
    }

    private void askForAuthor() {
        this.lineWriter.write("Who said it? ");
    }

    private String readInput() {
        return this.lineReader.readLine();
    }

}
