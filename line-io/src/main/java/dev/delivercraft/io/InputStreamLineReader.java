package dev.delivercraft.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * Reads lines from an {@link InputStream}.
 *
 * <p>This implementation uses the platform default charset, returns {@code null} when the stream is exhausted, and
 * wraps checked {@link IOException} failures in {@link UncheckedIOException}.
 */
public final class InputStreamLineReader implements LineReader {

    private final BufferedReader bufferedReader;

    /**
     * Creates a line reader backed by the supplied input stream.
     *
     * @param inputStream the stream to read from
     * @throws NullPointerException when {@code inputStream} is {@code null}
     */
    public InputStreamLineReader(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * Reads the next line from the stream.
     *
     * @return the next line without a line separator, or {@code null} when the stream is exhausted
     * @throws UncheckedIOException when the underlying stream cannot be read
     */
    @Override
    public String readLine() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
