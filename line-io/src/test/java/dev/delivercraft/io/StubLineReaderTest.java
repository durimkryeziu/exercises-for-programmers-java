package dev.delivercraft.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class StubLineReaderTest {

    @Test
    void readLine_GivenInputs_ShouldReturnInputsInOrder() {
        LineReader lineReader = new StubLineReader("first", "second");

        String first = lineReader.readLine();
        String second = lineReader.readLine();

        assertThat(first).isEqualTo("first");
        assertThat(second).isEqualTo("second");
    }

    @Test
    void readLine_GivenNoInputs_ShouldReturnNull() {
        LineReader lineReader = new StubLineReader();

        String result = lineReader.readLine();

        assertThat(result).isNull();
    }

    @Test
    void readLine_GivenExhaustedInputs_ShouldReturnNull() {
        LineReader lineReader = new StubLineReader("only");

        lineReader.readLine();
        String result = lineReader.readLine();

        assertThat(result).isNull();
    }

    @Test
    void readLine_GivenNullInputValue_ShouldReturnNull() {
        LineReader lineReader = new StubLineReader((String) null);

        String result = lineReader.readLine();

        assertThat(result).isNull();
    }

    @Test
    void constructor_GivenNullInputsArray_ShouldThrowException() {
        assertThatNullPointerException()
                .isThrownBy(() -> new StubLineReader((String[]) null))
                .withMessage("inputs must not be null");
    }
}
