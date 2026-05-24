package dev.delivercraft.paint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoundRoomTest {

    @Test
    void area_GivenRadius10_ShouldBePiTimesRadiusSquared() {
        RoundRoom room = new RoundRoom(RoomDimension.of("10"));

        BigDecimal area = room.area();

        BigDecimal expected = BigDecimal.valueOf(Math.PI)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
        assertThat(area).isEqualByComparingTo(expected);
    }

    @Test
    void area_GivenRadius1_ShouldBePi() {
        RoundRoom room = new RoundRoom(RoomDimension.of("1"));

        BigDecimal area = room.area();

        BigDecimal expected = BigDecimal.valueOf(Math.PI)
                .setScale(2, RoundingMode.HALF_UP);
        assertThat(area).isEqualByComparingTo(expected);
    }

    @Test
    void area_GivenDecimalRadius_ShouldCalculateCorrectly() {
        RoundRoom room = new RoundRoom(RoomDimension.of("5.5"));

        BigDecimal area = room.area();

        BigDecimal expected = BigDecimal.valueOf(Math.PI)
                .multiply(BigDecimal.valueOf(5.5))
                .multiply(BigDecimal.valueOf(5.5))
                .setScale(2, RoundingMode.HALF_UP);
        assertThat(area).isEqualByComparingTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "0.0", "-1", "-5"})
    void construction_GivenZeroOrNegativeRadius_ShouldThrow(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new RoundRoom(RoomDimension.of(input)));
    }

    @Test
    void construction_GivenNullRadius_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> new RoundRoom(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void estimate_GivenRoundRoomRadius10_ShouldCalculateOneGallon() {
        RoomDimensions room = new RoundRoom(RoomDimension.of("10"));

        PaintEstimate estimate = PaintEstimator.estimate(room);

        assertThat(estimate.gallons()).isEqualByComparingTo(BigInteger.ONE);
        assertThat(estimate.area()).isEqualTo("314.16");
    }

    @Test
    void estimate_GivenRoundRoomRadius15_ShouldCalculateThreeGallons() {
        RoomDimensions room = new RoundRoom(RoomDimension.of("15"));

        PaintEstimate estimate = PaintEstimator.estimate(room);

        assertThat(estimate.gallons()).isEqualByComparingTo(BigInteger.valueOf(3));
    }
}
