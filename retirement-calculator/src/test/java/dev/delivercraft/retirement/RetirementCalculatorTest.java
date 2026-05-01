package dev.delivercraft.retirement;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class RetirementCalculatorTest {

    @Test
    void asksForCurrentAge() {
        CapturingLineWriter lineWriter = new CapturingLineWriter();
        RetirementCalculator calculator = new RetirementCalculator(new StubLineReader("25", "65"), lineWriter,
                Year::now);

        calculator.printYearsLeft();

        assertThat(lineWriter.toString()).contains("What is your current age? ");
    }

    @Test
    void asksForRetirementAge() {
        CapturingLineWriter lineWriter = new CapturingLineWriter();
        RetirementCalculator calculator = new RetirementCalculator(new StubLineReader("25", "65"), lineWriter,
                Year::now);

        calculator.printYearsLeft();

        assertThat(lineWriter.toString()).contains("At what age would you like to retire? ");
    }

    @Test
    void displaysYearsLeftUntilRetirement() {
        CapturingLineWriter lineWriter = new CapturingLineWriter();
        RetirementCalculator calculator = new RetirementCalculator(new StubLineReader("25", "65"), lineWriter,
                new FakeYearProvider());

        calculator.printYearsLeft();

        assertThat(lineWriter.toString())
                .containsSequence("You have 40 years left until you can retire." + System.lineSeparator(),
                        "It's 2015, so you can retire in 2055." + System.lineSeparator());
    }

    @ParameterizedTest
    @CsvSource({"65, 65", "75, 65"})
    void displaysYouCanAlreadyRetireWhenCurrentAgeIsRetirementAgeOrMore(String currentAge, String retirementAge) {
        CapturingLineWriter lineWriter = new CapturingLineWriter();
        RetirementCalculator calculator = new RetirementCalculator(new StubLineReader(currentAge, retirementAge),
                lineWriter, Year::now);

        calculator.printYearsLeft();

        assertThat(lineWriter.toString()).contains("You can already retire.");
    }

    @ParameterizedTest
    @CsvSource({"'', 65", "25, ''"})
    void inputIsRequired(String currentAge, String retirementAge) {
        RetirementCalculator calculator = new RetirementCalculator(new StubLineReader(currentAge, retirementAge),
                new CapturingLineWriter(), Year::now);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::printYearsLeft)
                .withMessage("Input must not be empty!");
    }

    @ParameterizedTest
    @CsvSource({"abc, 65", "25, abc"})
    void inputMustBeANumber(String currentAge, String retirementAge) {
        RetirementCalculator calculator = new RetirementCalculator(new StubLineReader(currentAge, retirementAge),
                new CapturingLineWriter(), Year::now);

        assertThatIllegalArgumentException()
                .isThrownBy(calculator::printYearsLeft)
                .withMessage("Please enter a valid number! Input: abc");
    }

    @ParameterizedTest
    @CsvSource({"-1, 65", "25, -1"})
    void inputMustBeAPositiveNumber(String currentAge, String retirementAge) {
        RetirementCalculator calculator = new RetirementCalculator(new StubLineReader(currentAge, retirementAge),
                new CapturingLineWriter(), Year::now);

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
