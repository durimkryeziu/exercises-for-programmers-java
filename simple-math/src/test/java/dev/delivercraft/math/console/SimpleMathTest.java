package dev.delivercraft.math.console;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SimpleMathTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void inputIsRequired(String input) {
        LineReader lineReader = () -> input;
        SimpleMath simpleMath = new SimpleMath(lineReader, new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Input must not be empty!");
    }

    @ParameterizedTest
    @CsvSource({
            "abc, 5, Please enter a valid number! Input: abc",
            "-10, 5, Please enter a positive number! Input: -10",
            "10, asdf, Please enter a valid number! Input: asdf",
            "20, -5, Please enter a positive number! Input: -5"
    })
    void numbersMustBeValidPositiveNumbers(String firstInput, String secondInput, String expectedMessage) {
        LineReader lineReader = new StubLineReader(firstInput, secondInput);
        SimpleMath simpleMath = new SimpleMath(lineReader, new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage(expectedMessage);
    }

    @Test
    void secondNumberMustNotBeZero() {
        LineReader lineReader = new StubLineReader("10", "0");
        SimpleMath simpleMath = new SimpleMath(lineReader, new CapturingLineWriter());

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Cannot divide by zero!");
    }

    @Test
    void sumDifferenceProductAndQuotientIsPrinted() {
        LineReader lineReader = new StubLineReader("10", "5");
        LineWriter lineWriter = new CapturingLineWriter();
        SimpleMath simpleMath = new SimpleMath(lineReader, lineWriter);

        simpleMath.printOutput();

        assertThat(lineWriter.toString()).containsOnlyOnce("""
                10 + 5 = 15
                10 - 5 = 5
                10 * 5 = 50
                10 / 5 = 2
                """);
    }
}
