package dev.delivercraft.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CapturingLineWriterTest {

    private static final String GREETING = "Hello, World!";

    @Test
    void write_GivenText_ShouldCaptureTextWithoutLineSeparator() {
        LineWriter lineWriter = new CapturingLineWriter();

        lineWriter.write(GREETING);

        assertThat(lineWriter).hasToString(GREETING);
    }

    @Test
    void writeLine_GivenText_ShouldCaptureTextWithLineSeparator() {
        LineWriter lineWriter = new CapturingLineWriter();

        lineWriter.writeLine(GREETING);

        assertThat(lineWriter).hasToString(GREETING + System.lineSeparator());
    }

    @Test
    void writeAndWriteLine_GivenMixedCalls_ShouldPreserveOrder() {
        LineWriter lineWriter = new CapturingLineWriter();

        lineWriter.write("prompt ");
        lineWriter.writeLine("answer");

        assertThat(lineWriter).hasToString("prompt answer" + System.lineSeparator());
    }

    @Test
    void writeAndWriteLine_GivenNullValues_ShouldCaptureNullAsString() {
        LineWriter lineWriter = new CapturingLineWriter();

        lineWriter.write(null);
        lineWriter.writeLine(null);

        assertThat(lineWriter).hasToString("nullnull" + System.lineSeparator());
    }
}
