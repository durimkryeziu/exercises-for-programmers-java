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

class PizzaPurchasePlannerTest {

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
        LineWriter lineWriter = new CapturingLineWriter();
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
        LineWriter lineWriter = new CapturingLineWriter();
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
    void inputIsRequired(String people, String piecesPerPerson) {
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(people, piecesPerPerson),
                new CapturingLineWriter());

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Input must "
                + "not be empty!");
    }

    private static Stream<Arguments> missingInputs() {
        return Stream.of(
                Arguments.of("", TWO),
                Arguments.of(" ", TWO),
                Arguments.of(EIGHT, ""),
                Arguments.of(EIGHT, " "));
    }

    @Test
    void inputMustBeANumber() {
        CapturingLineWriter writer = new CapturingLineWriter();
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(ABC, TWO), writer);

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Please enter "
                + "a valid number! Input: abc");
    }

    @Test
    void inputMustBePositive() {
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(EIGHT, "-1"),
                new CapturingLineWriter());

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Please enter "
                + "a positive number! Input: -1");
    }

    @Test
    void peopleMustBeGreaterThanZero() {
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(new StubLineReader(ZERO, TWO),
                new CapturingLineWriter());

        assertThatIllegalArgumentException().isThrownBy(planner::displayPizzaPurchasePlan).withMessage("Number of "
                + "people must be greater than zero!");
    }
}
