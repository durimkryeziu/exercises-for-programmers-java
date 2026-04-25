package dev.delivercraft.io;

/**
 * Reads one line of text.
 */
public interface LineReader {

    /**
     * Reads one line of text.
     *
     * @return the line without a line separator, or {@code null} when no input remains
     */
    String readLine();
}
