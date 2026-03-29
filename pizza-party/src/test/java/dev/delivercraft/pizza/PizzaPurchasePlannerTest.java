package dev.delivercraft.pizza;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.StringJoiner;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PizzaPurchasePlannerTest {

    private static final int SINGLE_INPUT = 1;

    private static final String EIGHT = "8";

    private static final String FIVE = "5";

    private static final String FOUR = "4";

    private static final String TWO = "2";

    private static final String ONE = "1";

    private static final String ZERO = "0";

    private static final String ABC = "abc";

    private static final String PROMPT_OUTPUT =
            "How many people? How many pieces does each person want? " + System.lineSeparator();

    @ParameterizedTest
    @MethodSource("promptTexts")
    void asksForPrompts(String prompt) {
        LineReader lineReader = new StubLineReader("8", "2");
        LineWriter lineWriter = new InMemoryLineWriter();
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(lineReader, lineWriter);

        planner.displayPizzaPurchasePlan();

        assertThat(lineWriter.toString()).contains(prompt);
    }

    private static Stream<Arguments> promptTexts() {
        return Stream.of(Arguments.of("How many people?"), Arguments.of("How many pieces does each person want?"));
    }

    @ParameterizedTest
    @MethodSource("plannerOutputs")
    void displaysRequiredPizzaCount(String people, String piecesPerPerson, String expectedOutput) {
        LineReader lineReader = new StubLineReader(people, piecesPerPerson);
        LineWriter lineWriter = new InMemoryLineWriter();
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(lineReader, lineWriter);

        planner.displayPizzaPurchasePlan();

        assertThat(lineWriter).hasToString(expectedOutput);
    }

    private static Stream<Arguments> plannerOutputs() {
        return Stream.of(Arguments.of(EIGHT, TWO, PROMPT_OUTPUT + """
                You will need to purchase 2 pizzas.
                """), Arguments.of(EIGHT, ONE, PROMPT_OUTPUT + """
                You will need to purchase 1 pizza.
                """), Arguments.of(FOUR, FIVE, PROMPT_OUTPUT + """
                You will need to purchase 3 pizzas.
                """), Arguments.of(FOUR, ZERO, PROMPT_OUTPUT + """
                You will need to purchase 0 pizzas.
                """));
    }

    @ParameterizedTest
    @MethodSource("missingInputs")
    void inputIsRequired(String input) {
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(input), new InMemoryLineWriter());

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Input must "
                + "not be empty!");
    }

    private static Stream<String> missingInputs() {
        return Stream.of("", " ", System.lineSeparator() + TWO + System.lineSeparator(),
                EIGHT + System.lineSeparator() + System.lineSeparator());
    }

    @Test
    void inputMustBeANumber() {
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(ABC, TWO), new InMemoryLineWriter());

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Please enter "
                + "a valid number! Input: abc");
    }

    @Test
    void inputMustBePositive() {
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(EIGHT, "-1"),
                new InMemoryLineWriter());

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Please enter "
                + "a positive number! Input: -1");
    }

    @Test
    void peopleMustBeGreaterThanZero() {
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(ZERO, TWO),
                new InMemoryLineWriter());

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Number of "
                + "people must be greater than zero!");
    }

    private static final class StubLineReader implements LineReader {

        private final String[] inputs;

        private int index;

        private StubLineReader(String... inputs) {
            if (inputs.length == SINGLE_INPUT) {
                this.inputs = inputs[0].split("\\n", -1);
            } else {
                this.inputs = inputs;
            }
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
