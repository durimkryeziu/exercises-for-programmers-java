package com.craftsmanshipinsoftware.prntqts;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

class QuotesPrinter {

    private final InputStream inputStream;
    private final PrintStream printStream;

    QuotesPrinter(InputStream inputStream, PrintStream printStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        Objects.requireNonNull(printStream, "printStream must not be null");
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    void displayQuote() {
        try (Scanner scanner = new Scanner(this.inputStream)) {
            askForQuote();
            String quote = readInput(scanner);
            askForAuthor();
            String author = readInput(scanner);
            if (quote == null || quote.isBlank()) {
                this.printStream.println("Please enter the quote!");
            } else if (author == null || author.isBlank()) {
                this.printStream.println("Please enter the author!");
            } else {
                this.printStream.printf("%s says, \"%s\"%n", author, quote);
            }
        }
    }

    @SuppressWarnings("PMD.SystemPrintln")
    private void askForQuote() {
        System.out.print("What is the quote? ");
    }

    @SuppressWarnings("PMD.SystemPrintln")
    private void askForAuthor() {
        System.out.print("Who said it? ");
    }

    private String readInput(Scanner scanner) {
        return scanner.hasNext() ? scanner.nextLine() : null;
    }

}
