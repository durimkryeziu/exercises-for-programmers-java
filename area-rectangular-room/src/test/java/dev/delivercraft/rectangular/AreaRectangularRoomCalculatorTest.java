package dev.delivercraft.rectangular;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AreaRectangularRoomCalculatorTest {

    @Test
    void calculateArea_GivenNoInput_ShouldThrowException() {
        LineReader lineReader = () -> null;
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader,
                new InMemoryLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculateArea)
                .withMessage("Input must not be empty!");
    }

    @Test
    void calculateArea_ShouldAskForLength() {
        LineReader lineReader = () -> "123";
        LineWriter lineWriter = new InMemoryLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("What is the length of the room in feet?");
    }

    @Test
    void calculateArea_ShouldAskForWidth() {
        LineReader lineReader = () -> "123";
        LineWriter lineWriter = new InMemoryLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("What is the width of the room in feet?");
    }

    @Test
    void calculateArea_GivenLengthAndWidth_ShouldDisplayDimensions() {
        LineReader lineReader = new StubLineReader("15", "20");
        LineWriter lineWriter = new InMemoryLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("You entered dimensions of 15 feet by 20 feet." + System.lineSeparator());
    }

    @Test
    void calculateArea_GivenValidInput_ShouldDisplayTheAreaInSquareFeet() {
        LineReader lineReader = new StubLineReader("15", "20");
        LineWriter lineWriter = new InMemoryLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("The area is" + System.lineSeparator())
                .containsOnlyOnce("300 square feet" + System.lineSeparator());
    }

    @Test
    void calculateArea_GivenValidInput_ShouldDisplayTheAreaInSquareMeters() {
        LineReader lineReader = new StubLineReader("15", "20");
        LineWriter lineWriter = new InMemoryLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        calculator.calculateArea();

        assertThat(lineWriter.toString())
                .containsOnlyOnce("27.871 square meters" + System.lineSeparator());
    }

    @Test
    void calculateArea_GivenNonNumericInput_ShouldThrowException() {
        LineReader lineReader = new StubLineReader("asdf");
        LineWriter lineWriter = new InMemoryLineWriter();
        AreaRectangularRoomCalculator calculator = new AreaRectangularRoomCalculator(lineReader, lineWriter);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::calculateArea)
                .withMessage("Please enter a numeric value! Input: asdf");
    }

    private static final class StubLineReader implements LineReader {

        private final String[] inputs;

        private int index;

        private StubLineReader(String... inputs) {
            this.inputs = inputs;
        }

        @Override
        public String readLine() {
            return this.inputs[this.index++];
        }
    }

    private static final class InMemoryLineWriter implements LineWriter {

        private final StringJoiner stringJoiner = new StringJoiner("");

        @Override
        public void write(String text) {
            this.stringJoiner.add(text);
        }

        @Override
        public void writeLine(String line) {
            this.stringJoiner.add(line).add(System.lineSeparator());
        }

        @Override
        public String toString() {
            return this.stringJoiner.toString();
        }
    }
}
