package com.craftsmanshipinsoftware.math;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PositiveIntegerTest {

    @Test
    void valueMustBeANumber() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> PositiveInteger.of("asdf"))
                .withMessage("Please enter a valid number! Input: asdf");
    }

    @Test
    void valueMustBePositive() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> PositiveInteger.of("-1"))
                .withMessage("Please enter a positive number! Input: -1");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void valueMustNotNullOrEmpty(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> PositiveInteger.of(value))
                .withMessage("Please enter a valid number! Input: " + value);
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void encapsulatesAPositiveNumber() {
        PositiveInteger positiveInteger = PositiveInteger.of("42");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(positiveInteger.intValue()).isEqualTo(42);
            softly.assertThat(positiveInteger.longValue()).isEqualTo(42L);
            softly.assertThat(positiveInteger.floatValue()).isEqualTo(42.0f);
            softly.assertThat(positiveInteger.doubleValue()).isEqualTo(42.0);
            softly.assertThat(positiveInteger.hashCode()).isEqualTo(Integer.hashCode(42));
            softly.assertThat(positiveInteger).isEqualTo(PositiveInteger.of("42"));
            softly.assertThat(positiveInteger).hasToString("42");
        });
    }
}
