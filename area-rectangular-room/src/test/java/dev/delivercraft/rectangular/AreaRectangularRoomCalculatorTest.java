package dev.delivercraft.rectangular;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AreaRectangularRoomCalculatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    void calculateArea_GivenNoInput_ShouldThrowException(String input) {
        LineReader lineReader = () -> input;
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader,
                new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculateArea)
                .withMessage("Input must not be empty!");
    }

    @Test
    void calculateArea_ShouldAskForLength() {
        LineReader lineReader = () -> "123";
        LineWriter lineWriter = new CapturingLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("What is the length of the room in feet?");
    }

    @Test
    void calculateArea_ShouldAskForWidth() {
        LineReader lineReader = () -> "123";
        LineWriter lineWriter = new CapturingLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("What is the width of the room in feet?");
    }

    @Test
    void calculateArea_GivenLengthAndWidth_ShouldDisplayDimensions() {
        LineReader lineReader = new StubLineReader("15", "20");
        LineWriter lineWriter = new CapturingLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("You entered dimensions of 15 feet by 20 feet." + System.lineSeparator());
    }

    @Test
    void calculateArea_GivenValidInput_ShouldDisplayTheAreaInSquareFeet() {
        LineReader lineReader = new StubLineReader("15", "20");
        LineWriter lineWriter = new CapturingLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("The area is" + System.lineSeparator())
                .containsOnlyOnce("300 square feet" + System.lineSeparator());
    }

    @Test
    void calculateArea_GivenValidInput_ShouldDisplayTheAreaInSquareMeters() {
        LineReader lineReader = new StubLineReader("15", "20");
        LineWriter lineWriter = new CapturingLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("27.871 square meters" + System.lineSeparator());
    }

    @Test
    void calculateArea_GivenNonNumericInput_ShouldThrowException() {
        LineReader lineReader = new StubLineReader("asdf");
        LineWriter lineWriter = new CapturingLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculateArea)
                .withMessage("Please enter a numeric value! Input: asdf");
    }
}
