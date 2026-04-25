package dev.delivercraft.io;

import java.util.Arrays;
import java.util.Objects;

/**
 * Supplies canned line input to tests.
 *
 * <p>Inputs are returned in constructor order. When all inputs are exhausted, {@link #readLine()} returns
 * {@code null} to match end-of-input behavior. Multi-line strings are not split; each input line should be supplied as
 * a separate constructor argument.
 */
public final class StubLineReader implements LineReader {

    private final String[] inputs;

    private int index;

    /**
     * Creates a reader with canned input values.
     *
     * @param inputs the values returned by subsequent calls to {@link #readLine()}
     * @throws NullPointerException when {@code inputs} is {@code null}
     */
    public StubLineReader(String... inputs) {
        Objects.requireNonNull(inputs, "inputs must not be null");
        this.inputs = Arrays.copyOf(inputs, inputs.length);
    }

    /**
     * Returns the next canned input value.
     *
     * @return the next canned input, or {@code null} when no inputs remain
     */
    @Override
    public String readLine() {
        if (this.index >= this.inputs.length) {
            return null;
        }
        return this.inputs[this.index++];
    }
}
