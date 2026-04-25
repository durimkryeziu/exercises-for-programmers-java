package dev.delivercraft.io;

import java.io.StringWriter;

/**
 * Captures line writer output for state-based test assertions.
 *
 * <p>Writes are captured in call order. {@link #write(String)} appends text without a line separator, and
 * {@link #writeLine(String)} appends text followed by {@link System#lineSeparator()}.
 */
public final class CapturingLineWriter implements LineWriter {

    private final StringWriter output = new StringWriter();

    /**
     * Captures text without appending a line separator.
     *
     * @param text the text to capture
     */
    @Override
    public void write(String text) {
        this.output.write(text);
    }

    /**
     * Captures text followed by the platform line separator.
     *
     * @param line the line to capture
     */
    @Override
    public void writeLine(String line) {
        this.output.write(line);
        this.output.write(System.lineSeparator());
    }

    /**
     * Returns the captured output.
     *
     * @return the captured output
     */
    @Override
    public String toString() {
        return this.output.toString();
    }
}
