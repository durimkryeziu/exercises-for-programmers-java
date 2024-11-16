package com.craftsmanshipinsoftware.math.console;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SimpleMathTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "10\n", "\n5"})
    void inputIsRequired(String input) {
        SimpleMath simpleMath = new SimpleMath(new ByteArrayInputStream(input.getBytes()),
                new PrintStream(new ByteArrayOutputStream()));

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Input must not be empty!");
    }

    @Test
    void firstInputMustBeANumber() {
        SimpleMath simpleMath = new SimpleMath(new ByteArrayInputStream("abc".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Please enter a valid number! Input: abc");
    }

    @Test
    void firstNumberMustBePositive() {
        SimpleMath simpleMath = new SimpleMath(new ByteArrayInputStream("-10\n5".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Please enter a positive number! Input: -10");
    }

    @Test
    void secondInputMustBeANumber() {
        SimpleMath simpleMath = new SimpleMath(new ByteArrayInputStream("10\nasdf".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Please enter a valid number! Input: asdf");
    }

    @Test
    void secondNumberMustBePositive() {
        SimpleMath simpleMath = new SimpleMath(new ByteArrayInputStream("10\n-5".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Please enter a positive number! Input: -5");
    }

    @Test
    void secondNumberMustNotBeZero() {
        SimpleMath simpleMath = new SimpleMath(new ByteArrayInputStream("10\n0".getBytes()),
                new PrintStream(new ByteArrayOutputStream()));

        assertThatIllegalArgumentException()
                .isThrownBy(simpleMath::printOutput)
                .withMessage("Cannot divide by zero!");
    }

    @Test
    void sumDifferenceProductAndQuotientIsPrinted() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SimpleMath simpleMath = new SimpleMath(new ByteArrayInputStream("10\n5".getBytes()),
                new PrintStream(outputStream));

        simpleMath.printOutput();

        assertThat(outputStream).hasToString("""
                10 + 5 = 15
                10 - 5 = 5
                10 * 5 = 50
                10 / 5 = 2
                """);
    }
}
