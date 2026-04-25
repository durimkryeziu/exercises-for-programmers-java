package dev.delivercraft.io;

/**
 * Writes text and lines of text.
 */
public interface LineWriter {

    /**
     * Writes text without appending a line separator.
     *
     * @param text the text to write
     */
    void write(String text);

    /**
     * Writes text followed by the platform line separator.
     *
     * @param line the line to write
     */
    void writeLine(String line);
}
