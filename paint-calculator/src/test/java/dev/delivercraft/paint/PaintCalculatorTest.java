package dev.delivercraft.paint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.stream.Stream;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class PaintCalculatorTest {

    private static final String VALID_LENGTH = "18";

    private static final String VALID_WIDTH = "20";

    private static final String EMPTY_INPUT = "";

    private static final String NON_NUMERIC_INPUT = "abc";

    private static final String ZERO_INPUT = "0";

    private static final String NEGATIVE_INPUT = "-1";

    private static final String SCIENTIFIC_INPUT = "1e3";

    private static final String SHORT_DECIMAL = ".5";

    private static final String LENGTH_PROMPT = "What is the length of the room in feet? ";

    private static final String WIDTH_PROMPT = "What is the width of the room in feet? ";

    @Test
    void calculatePaint_GivenExampleDimensions_ShouldDisplayGallonsAndArea() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader(VALID_LENGTH, VALID_WIDTH), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 2 gallons of paint to cover 360 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenSmallCeiling_ShouldUseSingularGallon() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader("10", "10"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 1 gallon of paint to cover 100 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenFractionalDimensions_ShouldDisplayExactDecimalArea() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader("10.5", "10.1"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 1 gallon of paint to cover 106.05 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenAreaJustOverOneGallon_ShouldRoundGallonsUp() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader("35.1", "10"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 2 gallons of paint to cover 351 square feet."
                + System.lineSeparator());
    }

    @ParameterizedTest
    @MethodSource("invalidInputFlows")
    void calculatePaint_GivenInvalidInput_ShouldFailFast(String length, String width, String expectedOutput) {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader(length, width), lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculatePaint);

        assertThat(lineWriter).hasToString(expectedOutput);
    }

    private static Stream<Arguments> invalidInputFlows() {
        return Stream.of(
                Arguments.of(EMPTY_INPUT, VALID_WIDTH, LENGTH_PROMPT),
                Arguments.of(null, VALID_WIDTH, LENGTH_PROMPT),
                Arguments.of(NON_NUMERIC_INPUT, VALID_WIDTH, LENGTH_PROMPT),
                Arguments.of(ZERO_INPUT, VALID_WIDTH, LENGTH_PROMPT),
                Arguments.of(NEGATIVE_INPUT, VALID_WIDTH, LENGTH_PROMPT),
                Arguments.of(SCIENTIFIC_INPUT, VALID_WIDTH, LENGTH_PROMPT),
                Arguments.of(SHORT_DECIMAL, VALID_WIDTH, LENGTH_PROMPT),
                Arguments.of(VALID_LENGTH, EMPTY_INPUT, LENGTH_PROMPT + WIDTH_PROMPT),
                Arguments.of(VALID_LENGTH, null, LENGTH_PROMPT + WIDTH_PROMPT),
                Arguments.of(VALID_LENGTH, NON_NUMERIC_INPUT, LENGTH_PROMPT + WIDTH_PROMPT),
                Arguments.of(VALID_LENGTH, ZERO_INPUT, LENGTH_PROMPT + WIDTH_PROMPT),
                Arguments.of(VALID_LENGTH, NEGATIVE_INPUT, LENGTH_PROMPT + WIDTH_PROMPT),
                Arguments.of(VALID_LENGTH, SCIENTIFIC_INPUT, LENGTH_PROMPT + WIDTH_PROMPT),
                Arguments.of(VALID_LENGTH, SHORT_DECIMAL, LENGTH_PROMPT + WIDTH_PROMPT));
    }

    @ParameterizedTest
    @ValueSource(strings = {"18", " 18 ", "18.0", "0.5"})
    void calculatePaint_GivenPlainDecimalInput_ShouldAcceptInput(String input) {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader(input, "20"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter.toString()).contains("You will need to purchase");
    }
}
