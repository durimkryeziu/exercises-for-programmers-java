package dev.delivercraft.rectangular;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

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
}
