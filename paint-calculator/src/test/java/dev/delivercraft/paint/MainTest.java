package dev.delivercraft.paint;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class MainTest {

    @Test
    @ResourceLock("SYSTEM_IN")
    @ResourceLock(Resources.SYSTEM_OUT)
    @SuppressWarnings("PMD.CloseResource")
    void main_GivenValidInput_ShouldPrintPaintEstimate() throws IOException {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        String inputText = "1%n18%n20%n".formatted();
        ByteArrayInputStream input = new ByteArrayInputStream(inputText.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (input; output; PrintStream printStream = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(input);
            System.setOut(printStream);

            new Main().main();

            assertThat(output.toString(StandardCharsets.UTF_8)).contains(
                    "You will need to purchase 2 gallons of paint to cover 360 square feet.");
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}
