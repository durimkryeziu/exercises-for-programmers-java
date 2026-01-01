package dev.delivercraft.rectangular;

import java.io.OutputStream;
import java.io.PrintWriter;

final class OutputStreamLineWriter implements LineWriter {

    private final PrintWriter printWriter;

    OutputStreamLineWriter(OutputStream outputStream) {
        this.printWriter = new PrintWriter(outputStream, true);
    }

    @Override
    public void write(String text) {
        this.printWriter.print(text);
        this.printWriter.flush();
    }

    @Override
    public void writeLine(String line) {
        this.printWriter.println(line);
    }
}
