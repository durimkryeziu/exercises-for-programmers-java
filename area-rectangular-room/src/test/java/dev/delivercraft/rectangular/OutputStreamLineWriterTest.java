package dev.delivercraft.rectangular;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class OutputStreamLineWriterTest {

    @Test
    void write_GivenNull_ShouldPrintNullAsString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        LineWriter lineWriter = new OutputStreamLineWriter(outputStream);

        lineWriter.write(null);

        assertThat(outputStream).hasToString("null");
    }

    @Test
    void write_GivenText_ShouldPrintText() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        LineWriter lineWriter = new OutputStreamLineWriter(outputStream);
        String text = "Hello, World!";

        lineWriter.write(text);

        assertThat(outputStream).hasToString(text);
    }

    @Test
    void writeLine_GivenText_ShouldPrintTextWithNewLine() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        LineWriter lineWriter = new OutputStreamLineWriter(outputStream);
        String line = "Hello, World!";

        lineWriter.writeLine(line);

        assertThat(outputStream).hasToString(line + System.lineSeparator());
    }
}
