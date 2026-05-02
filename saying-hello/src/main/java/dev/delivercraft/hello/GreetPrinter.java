package dev.delivercraft.hello;

import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;

import java.util.Objects;

final class GreetPrinter {

    private final LineReader lineReader;

    private final LineWriter lineWriter;

    GreetPrinter(LineReader lineReader, LineWriter lineWriter) {
        Objects.requireNonNull(lineReader, "lineReader must not be null");
        Objects.requireNonNull(lineWriter, "lineWriter must not be null");
        this.lineReader = lineReader;
        this.lineWriter = lineWriter;
    }

    void sayHello() {
        this.lineWriter.write("What is your name? ");
        String name = this.lineReader.readLine();
        this.lineWriter.writeLine("Hello, %s, nice to meet you!".formatted(name));
    }

}
