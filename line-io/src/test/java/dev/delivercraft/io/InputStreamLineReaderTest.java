package dev.delivercraft.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class InputStreamLineReaderTest {

    @Test
    void readLine_GivenNoInput_ShouldReturnNull() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{});
        LineReader lineReader = new InputStreamLineReader(inputStream);

        String result = lineReader.readLine();

        assertThat(result).isNull();
    }

    @Test
    void readLine_GivenInput_ShouldReturnInputLine() {
        String input = "Hello, World!\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LineReader lineReader = new InputStreamLineReader(inputStream);

        String result = lineReader.readLine();

        assertThat(result).isEqualTo("Hello, World!");
    }

    @Test
    void readLine_GivenFailingInputStream_ShouldThrowUncheckedIOException() throws IOException {
        @SuppressWarnings("PMD.CloseResource") // This is to simulate a closed stream for testing
        InputStream inputStream = InputStream.nullInputStream();
        inputStream.close();

        LineReader lineReader = new InputStreamLineReader(inputStream);

        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(lineReader::readLine)
                .withCauseInstanceOf(IOException.class);
    }

    @Test
    void constructor_GivenNullInputStream_ShouldThrowException() {
        assertThatNullPointerException()
                .isThrownBy(() -> new InputStreamLineReader(null))
                .withMessage("inputStream must not be null");
    }
}
