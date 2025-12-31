package dev.delivercraft.retirement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RetirementCalculatorTest {

    @Test
    void asksForCurrentAge() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        RetirementCalculator calculator = new RetirementCalculator(new ByteArrayInputStream("25\n65".getBytes()),
                new PrintStream(outputStream), Year::now);

        calculator.printYearsLeft();

        assertThat(outputStream.toString()).contains("What is your current age?");
    }

    @Test
    void asksForRetirementAge() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        RetirementCalculator calculator = new RetirementCalculator(new ByteArrayInputStream("25\n65".getBytes()),
                new PrintStream(outputStream), Year::now);

        calculator.printYearsLeft();

        assertThat(outputStream.toString()).contains("At what age would you like to retire?");
    }

    @Test
    void displaysYearsLeftUntilRetirement() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        RetirementCalculator calculator = new RetirementCalculator(new ByteArrayInputStream("25\n65".getBytes()),
                new PrintStream(outputStream), new FakeYearProvider());

        calculator.printYearsLeft();

        assertThat(outputStream.toString()).containsSequence("You have 40 years left until you can retire.\n",
                "It's 2015, so you can retire in 2055.\n");
    }

    @ParameterizedTest
    @ValueSource(strings = {"65\n65", "75\n65"})
    void displaysYouCanAlreadyRetireWhenCurrentAgeIsRetirementAgeOrMore(String input) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        RetirementCalculator calculator = new RetirementCalculator(new ByteArrayInputStream(input.getBytes()),
                new PrintStream(outputStream), Year::now);

        calculator.printYearsLeft();

        assertThat(outputStream.toString()).contains("You can already retire.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"\n65", "25\n"})
    void inputIsRequired(String input) {
        RetirementCalculator calculator = new RetirementCalculator(new ByteArrayInputStream(input.getBytes()),
                new PrintStream(new ByteArrayOutputStream()), Year::now);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::printYearsLeft)
                .withMessage("Input must not be empty!");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc\n65", "25\nabc"})
    void inputMustBeANumber(String input) {
        RetirementCalculator calculator = new RetirementCalculator(new ByteArrayInputStream(input.getBytes()),
                new PrintStream(new ByteArrayOutputStream()), Year::now);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::printYearsLeft)
                .withMessage("Please enter a valid number! Input: abc");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1\n65", "25\n-1"})
    void inputMustBeAPositiveNumber(String input) {
        RetirementCalculator calculator = new RetirementCalculator(new ByteArrayInputStream(input.getBytes()),
                new PrintStream(new ByteArrayOutputStream()), Year::now);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::printYearsLeft)
                .withMessage("Please enter a positive number! Input: -1");
    }

    static final class FakeYearProvider implements YearProvider {

        @Override
        public Year currentYear() {
            return Year.of(2015);
        }
    }
}
