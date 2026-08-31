package dev.delivercraft.paint;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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

    private static final String SHAPE_MENU = "Select room shape: 1) Rectangular  2) Round  3) L-shaped";

    private static final String RADIUS_PROMPT = "What is the radius of the room in feet? ";

    private static final String OUTER_LENGTH_PROMPT = "What is the length of the outer rectangle in feet? ";

    private static final String OUTER_WIDTH_PROMPT = "What is the width of the outer rectangle in feet? ";

    private static final String CUTOUT_LENGTH_PROMPT = "What is the length of the cut-out in feet? ";

    private static final String CUTOUT_WIDTH_PROMPT = "What is the width of the cut-out in feet? ";

    private static final String L_SHAPED_SHAPE = "3";

    private static final String RECTANGULAR_SHAPE = "1";

    private static final String TEN_FEET = "10";

    private static final String INVALID_LENGTH_OUTPUT =
            SHAPE_MENU + System.lineSeparator() + LENGTH_PROMPT;

    private static final String INVALID_WIDTH_OUTPUT =
            SHAPE_MENU + System.lineSeparator() + LENGTH_PROMPT + WIDTH_PROMPT;

    @Test
    void calculatePaint_GivenExampleDimensions_ShouldDisplayGallonsAndArea() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(RECTANGULAR_SHAPE, VALID_LENGTH, VALID_WIDTH), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 2 gallons of paint to cover 360 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenSmallCeiling_ShouldUseSingularGallon() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(RECTANGULAR_SHAPE, TEN_FEET, TEN_FEET), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 1 gallon of paint to cover 100 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenFractionalDimensions_ShouldDisplayExactDecimalArea() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(RECTANGULAR_SHAPE, "10.5", "10.1"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 1 gallon of paint to cover 106.05 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenAreaJustOverOneGallon_ShouldRoundGallonsUp() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(RECTANGULAR_SHAPE, "35.1", "10"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + LENGTH_PROMPT + WIDTH_PROMPT
                + "You will need to purchase 2 gallons of paint to cover 351 square feet."
                + System.lineSeparator());
    }

    @ParameterizedTest
    @MethodSource("invalidInputFlows")
    void calculatePaint_GivenInvalidInput_ShouldFailFast(String shape, String length, String width,
                                                         String expectedOutput) {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader(shape, length, width), lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculatePaint);

        assertThat(lineWriter).hasToString(expectedOutput);
    }

    private static Stream<Arguments> invalidInputFlows() {
        return Stream.of(
                Arguments.of(RECTANGULAR_SHAPE, EMPTY_INPUT, VALID_WIDTH, INVALID_LENGTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, null, VALID_WIDTH, INVALID_LENGTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, NON_NUMERIC_INPUT, VALID_WIDTH, INVALID_LENGTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, ZERO_INPUT, VALID_WIDTH, INVALID_LENGTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, NEGATIVE_INPUT, VALID_WIDTH, INVALID_LENGTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, SCIENTIFIC_INPUT, VALID_WIDTH, INVALID_LENGTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, SHORT_DECIMAL, VALID_WIDTH, INVALID_LENGTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, VALID_LENGTH, EMPTY_INPUT, INVALID_WIDTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, VALID_LENGTH, null, INVALID_WIDTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, VALID_LENGTH, NON_NUMERIC_INPUT, INVALID_WIDTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, VALID_LENGTH, ZERO_INPUT, INVALID_WIDTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, VALID_LENGTH, NEGATIVE_INPUT, INVALID_WIDTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, VALID_LENGTH, SCIENTIFIC_INPUT, INVALID_WIDTH_OUTPUT),
                Arguments.of(RECTANGULAR_SHAPE, VALID_LENGTH, SHORT_DECIMAL, INVALID_WIDTH_OUTPUT));
    }

    @ParameterizedTest
    @ValueSource(strings = {"18", " 18 ", "18.0", "0.5"})
    void calculatePaint_GivenPlainDecimalInput_ShouldAcceptInput(String input) {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(RECTANGULAR_SHAPE, input, VALID_WIDTH), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter.toString()).contains("You will need to purchase");
    }

    @Test
    void calculatePaint_GivenRoundRoomSelection_ShouldCalculateCorrectArea() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader("2", "10"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + RADIUS_PROMPT
                + "You will need to purchase 1 gallon of paint to cover 314.16 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenLShapedRoomSelection_ShouldCalculateCorrectArea() {
        // outer 20*15 = 300 ; cutout 5*4 = 20 ; area = 280 ; 280/350 -> ceil 1
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(L_SHAPED_SHAPE, VALID_WIDTH, "15", "5", "4"), lineWriter);

        calculator.calculatePaint();

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + OUTER_LENGTH_PROMPT + OUTER_WIDTH_PROMPT
                + CUTOUT_LENGTH_PROMPT + CUTOUT_WIDTH_PROMPT
                + "You will need to purchase 1 gallon of paint to cover 280 square feet."
                + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenLShapedRoomWithInvalidOuterLength_ShouldFailFastBeforePromptingWidth() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(L_SHAPED_SHAPE, NON_NUMERIC_INPUT), lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculatePaint);

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator() + OUTER_LENGTH_PROMPT);
    }

    @Test
    void calculatePaint_GivenLShapedRoomWithInvalidCutoutWidth_ShouldFailFastAfterAllPriorPrompts() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(L_SHAPED_SHAPE, VALID_WIDTH, "15", "5", NEGATIVE_INPUT), lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculatePaint);

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + OUTER_LENGTH_PROMPT + OUTER_WIDTH_PROMPT
                + CUTOUT_LENGTH_PROMPT + CUTOUT_WIDTH_PROMPT);
    }

    @Test
    void calculatePaint_GivenCutoutNotSmallerThanOuter_ShouldThrowWithDescriptiveMessage() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(
                new StubLineReader(L_SHAPED_SHAPE, VALID_WIDTH, "15", VALID_WIDTH, "4"), lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculatePaint)
                .withMessage("Cut-out must be smaller than the outer rectangle on both length and width.");

        assertThat(lineWriter).hasToString(SHAPE_MENU + System.lineSeparator()
                + OUTER_LENGTH_PROMPT + OUTER_WIDTH_PROMPT
                + CUTOUT_LENGTH_PROMPT + CUTOUT_WIDTH_PROMPT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4", "0", "abc", "", "  "})
    void calculatePaint_GivenInvalidShapeInput_ShouldThrowIllegalArgumentException(String invalidInput) {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(new StubLineReader(invalidInput), lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculatePaint)
                .withMessage("Please enter 1, 2 or 3");
        assertThat(lineWriter.toString()).isEqualTo(SHAPE_MENU + System.lineSeparator());
    }

    @Test
    void calculatePaint_GivenNullShapeInput_ShouldThrowIllegalArgumentException() {
        LineWriter lineWriter = new CapturingLineWriter();
        PaintCalculator calculator = new PaintCalculator(() -> null, lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculatePaint)
                .withMessage("Please enter 1, 2 or 3");
        assertThat(lineWriter.toString()).isEqualTo(SHAPE_MENU + System.lineSeparator());
    }
}
