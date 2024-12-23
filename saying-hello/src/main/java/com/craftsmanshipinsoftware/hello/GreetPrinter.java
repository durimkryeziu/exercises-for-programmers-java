package com.craftsmanshipinsoftware.hello;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

final class GreetPrinter {

    private final InputStream inputStream;
    private final PrintStream printStream;

    GreetPrinter(InputStream inputStream, PrintStream printStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        Objects.requireNonNull(printStream, "printStream must not be null");
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    void sayHello() {
        askForName();
        String name = readName();
        this.printStream.printf("Hello, %s, nice to meet you!%n", name);
    }

    @SuppressWarnings("PMD.SystemPrintln")
    private void askForName() {
        System.out.print("What is your name? ");
    }

    private String readName() {
        try (Scanner scanner = new Scanner(this.inputStream)) {
            return scanner.nextLine();
        }
    }

}
