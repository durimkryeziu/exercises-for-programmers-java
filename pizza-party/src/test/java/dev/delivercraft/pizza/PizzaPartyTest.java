package dev.delivercraft.pizza;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.StringJoiner;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PizzaPartyTest {

    private static final int SINGLE_INPUT = 1;

    private static final String FIRST_INPUT = Integer.toString(8);

    private static final String SECOND_NUMBER = Integer.toString(2);

    private static final String THIRD_NUMBER = Integer.toString(8);

    private static final String FOURTH_NUMBER = Integer.toString(4);

    private static final String SINGLE_PIECE = Integer.toString(1);

    private static final String PROMPT_OUTPUT =
            "How many people? How many pizzas do you have? How many slices per pizza? " + System.lineSeparator();

    @ParameterizedTest
    @MethodSource("promptTexts")
    void asksForPrompts(String prompt) {
        LineReader lineReader = new StubLineReader(FIRST_INPUT, SECOND_NUMBER, THIRD_NUMBER);
        LineWriter lineWriter = new InMemoryLineWriter();
        PizzaParty pizzaParty = new PizzaParty(lineReader, lineWriter);

        pizzaParty.displayPizzaParty();

        assertThat(lineWriter.toString()).contains(prompt);
    }

    private static Stream<Arguments> promptTexts() {
        return Stream.of(
                Arguments.of("How many people?"),
                Arguments.of("How many pizzas do you have?"),
                Arguments.of("How many slices per pizza?"));
    }

    @ParameterizedTest
    @MethodSource("pizzaOutputs")
    void displaysPizzaOutput(String people, String pizzas, String slicesPerPizza, String expectedOutput) {
        LineReader lineReader = new StubLineReader(people, pizzas, slicesPerPizza);
        LineWriter lineWriter = new InMemoryLineWriter();
        PizzaParty pizzaParty = new PizzaParty(lineReader, lineWriter);

        pizzaParty.displayPizzaParty();

        assertThat(lineWriter).hasToString(expectedOutput);
    }

    private static Stream<Arguments> pizzaOutputs() {
        return Stream.of(
                Arguments.of(FIRST_INPUT, SECOND_NUMBER, THIRD_NUMBER, PROMPT_OUTPUT + """
                        8 people with 2 pizzas
                        Each person gets 2 pieces of pizza.
                        There are 0 leftover pieces.
                        """),
                Arguments.of(FIRST_INPUT, SINGLE_PIECE, THIRD_NUMBER, PROMPT_OUTPUT + """
                        8 people with 1 pizza
                        Each person gets 1 piece of pizza.
                        There are 0 leftover pieces.
                        """),
                Arguments.of(FOURTH_NUMBER, SINGLE_PIECE, Integer.toString(7), PROMPT_OUTPUT + """
                        4 people with 1 pizza
                        Each person gets 1 piece of pizza.
                        There are 3 leftover pieces.
                        """),
                Arguments.of(FOURTH_NUMBER, SINGLE_PIECE, Integer.toString(5), PROMPT_OUTPUT + """
                        4 people with 1 pizza
                        Each person gets 1 piece of pizza.
                        There is 1 leftover piece.
                        """));
    }

    @ParameterizedTest
    @MethodSource("missingInputs")
    void inputIsRequired(String input) {
        PizzaParty pizzaParty = new PizzaParty(new StubLineReader(input), new InMemoryLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Input must not be empty!");
    }

    private static Stream<String> missingInputs() {
        return Stream.of(
                "",
                " ",
                System.lineSeparator() + SECOND_NUMBER + System.lineSeparator() + THIRD_NUMBER,
                FIRST_INPUT + System.lineSeparator() + System.lineSeparator() + THIRD_NUMBER,
                FIRST_INPUT + System.lineSeparator() + SECOND_NUMBER + System.lineSeparator());
    }

    @Test
    void inputMustBeANumber() {
        PizzaParty pizzaParty = new PizzaParty(
                new StubLineReader("abc", SECOND_NUMBER, THIRD_NUMBER), new InMemoryLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Please enter a valid number! Input: abc");
    }

    @Test
    void inputMustBePositive() {
        PizzaParty pizzaParty = new PizzaParty(
                new StubLineReader("-1", SECOND_NUMBER, THIRD_NUMBER), new InMemoryLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Please enter a positive number! Input: -1");
    }

    @Test
    void peopleMustBeGreaterThanZero() {
        PizzaParty pizzaParty = new PizzaParty(new StubLineReader("0", SECOND_NUMBER, THIRD_NUMBER),
                new InMemoryLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Number of people must be greater than zero!");
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
