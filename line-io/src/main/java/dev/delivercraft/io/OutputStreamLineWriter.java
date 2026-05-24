package dev.delivercraft.io;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Writes text and lines to an {@link OutputStream}.
 *
 * <p>This implementation writes text without validation or formatting, flushes prompt text written through
 * {@link #write(String)}, and uses {@link PrintWriter#println(String)} for line output.
 */
public final class OutputStreamLineWriter implements LineWriter {

    private final PrintWriter printWriter;

    /**
     * Creates a line writer backed by the supplied output stream.
     *
     * @param outputStream the stream to write to
     * @throws NullPointerException when {@code outputStream} is {@code null}
     */
    public OutputStreamLineWriter(OutputStream outputStream) {
        Objects.requireNonNull(outputStream, "outputStream must not be null");
        this.printWriter = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);
    }

    /**
     * Writes text without appending a line separator and flushes the underlying writer.
     *
     * @param text the text to write
     */
    @Override
    public void write(String text) {
        this.printWriter.print(text);
        this.printWriter.flush();
    }

    /**
     * Writes text followed by the platform line separator.
     *
     * @param line the line to write
     */
    @Override
    public void writeLine(String line) {
        this.printWriter.println(line);
    }
}
