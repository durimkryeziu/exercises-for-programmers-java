package dev.delivercraft.madlib;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MadLibTest {

    private static final String DOG = "dog";

    @ParameterizedTest(name = "Given input [{0}]")
    @MethodSource("missingInputCases")
    void printStory_GivenInputIsMissing_ShouldAskForMissingInput(String... input) {
        LineWriter lineWriter = new CapturingLineWriter();
        MadLib madLib = new MadLib(new StubLineReader(input), lineWriter);

        madLib.printStory();

        assertThat(lineWriter.toString()).contains("Please enter something as input!" + System.lineSeparator());
    }

    private static Stream<Arguments> missingInputCases() {
        return Stream.of(
                Arguments.of((Object) new String[]{""}),
                Arguments.of((Object) new String[]{DOG}),
                Arguments.of((Object) new String[]{DOG, "walk"}),
                Arguments.of((Object) new String[]{DOG, "walk", "blue"}));
    }

    @Test
    void printStory_GivenAllNeededInput_ShouldPrintStory() {
        LineWriter lineWriter = new CapturingLineWriter();
        MadLib madLib = new MadLib(new StubLineReader(DOG, "walk", "blue", "quickly"), lineWriter);

        madLib.printStory();

        assertThat(lineWriter.toString()).contains("Do you walk your blue dog quickly? That's hilarious!"
                + System.lineSeparator());
    }

}
