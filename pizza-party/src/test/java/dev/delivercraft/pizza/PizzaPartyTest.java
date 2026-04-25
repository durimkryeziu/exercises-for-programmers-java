package dev.delivercraft.pizza;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PizzaPartyTest {

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
        LineWriter lineWriter = new CapturingLineWriter();
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
        LineWriter lineWriter = new CapturingLineWriter();
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
    void inputIsRequired(String people, String pizzas, String slicesPerPizza) {
        PizzaParty pizzaParty = new PizzaParty(new StubLineReader(people, pizzas, slicesPerPizza),
                new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Input must not be empty!");
    }

    private static Stream<Arguments> missingInputs() {
        return Stream.of(
                Arguments.of("", SECOND_NUMBER, THIRD_NUMBER),
                Arguments.of(" ", SECOND_NUMBER, THIRD_NUMBER),
                Arguments.of(FIRST_INPUT, "", THIRD_NUMBER),
                Arguments.of(FIRST_INPUT, " ", THIRD_NUMBER),
                Arguments.of(FIRST_INPUT, SECOND_NUMBER, ""),
                Arguments.of(FIRST_INPUT, SECOND_NUMBER, " "));
    }

    @Test
    void inputMustBeANumber() {
        PizzaParty pizzaParty = new PizzaParty(
                new StubLineReader("abc", SECOND_NUMBER, THIRD_NUMBER), new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Please enter a valid number! Input: abc");
    }

    @Test
    void inputMustBePositive() {
        PizzaParty pizzaParty = new PizzaParty(
                new StubLineReader("-1", SECOND_NUMBER, THIRD_NUMBER), new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Please enter a positive number! Input: -1");
    }

    @Test
    void peopleMustBeGreaterThanZero() {
        PizzaParty pizzaParty = new PizzaParty(new StubLineReader("0", SECOND_NUMBER, THIRD_NUMBER),
                new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(pizzaParty::displayPizzaParty)
                .withMessage("Number of people must be greater than zero!");
    }
}
