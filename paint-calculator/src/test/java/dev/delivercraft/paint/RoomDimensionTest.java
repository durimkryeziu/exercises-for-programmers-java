package dev.delivercraft.paint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RoomDimensionTest {

    @ParameterizedTest
    @ValueSource(strings = {"10", "10.5", "0.5", " 10.50 "})
    void of_GivenPlainPositiveDecimal_ShouldReturnDimension(String input) {
        RoomDimension dimension = RoomDimension.of(input);

        assertThat(dimension.value()).isEqualByComparingTo(new BigDecimal(input.trim()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "abc", "1e3", ".5", "10.", "0", "0.0", "-1", "-1.5"})
    void of_GivenInvalidInput_ShouldThrowException(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> RoomDimension.of(input));
    }

    @Test
    void of_GivenSameNumericValue_ShouldCreateEqualDimensions() {
        RoomDimension dimension = RoomDimension.of("10.0");
        RoomDimension sameValue = RoomDimension.of("10.00");

        assertThat(dimension).isEqualTo(sameValue);
        assertThat(dimension.hashCode()).isEqualTo(sameValue.hashCode());
    }
}
