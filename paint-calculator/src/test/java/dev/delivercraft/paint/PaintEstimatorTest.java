package dev.delivercraft.paint;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class PaintEstimatorTest {

    @Test
    void estimate_GivenExampleDimensions_ShouldCalculateAreaAndGallons() {
        RoomDimensions dimensions = new RectangularRoom(
                RoomDimension.of("18"),
                RoomDimension.of("20"));

        PaintEstimate estimate = PaintEstimator.estimate(dimensions);

        assertThat(estimate.area()).hasToString("360");
        assertThat(estimate.gallons()).isEqualTo(BigInteger.valueOf(2));
    }

    @Test
    void estimate_GivenAreaJustOverOneGallon_ShouldRoundGallonsUp() {
        RoomDimensions dimensions = new RectangularRoom(
                RoomDimension.of("35.1"),
                RoomDimension.of("10"));

        PaintEstimate estimate = PaintEstimator.estimate(dimensions);

        assertThat(estimate.area()).isEqualTo("351");
        assertThat(estimate.gallons()).isEqualTo(BigInteger.valueOf(2));
    }
}
