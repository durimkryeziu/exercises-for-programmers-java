package dev.delivercraft.paint;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LShapedRoomTest {

    private static final RectangularRoom ANY_OUTER =
            new RectangularRoom(RoomDimension.of("20"), RoomDimension.of("15"));

    private static final RectangularRoom ANY_CUTOUT =
            new RectangularRoom(RoomDimension.of("5"), RoomDimension.of("4"));

    private static final String CUTOUT_TOO_BIG_MSG =
            "Cut-out must be smaller than the outer rectangle on both length and width.";

    @Test
    void construction_GivenNullOuter_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> new LShapedRoom(null, ANY_CUTOUT))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("outer must not be null");
    }

    @Test
    void construction_GivenNullCutout_ShouldThrowNullPointerException() {
        assertThatThrownBy(() -> new LShapedRoom(ANY_OUTER, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("cutout must not be null");
    }

    @Test
    void area_GivenOuter20x15AndCutout5x4_ShouldReturn280() {
        LShapedRoom room = new LShapedRoom(ANY_OUTER, ANY_CUTOUT);

        assertThat(room.area()).isEqualByComparingTo(new BigDecimal("280"));
    }

    @Test
    void area_GivenFractionalDimensions_ShouldComputeExactly() {
        LShapedRoom room = new LShapedRoom(
                new RectangularRoom(RoomDimension.of("10.5"), RoomDimension.of("10.1")),
                new RectangularRoom(RoomDimension.of("2.5"), RoomDimension.of("2")));

        // 10.5 * 10.1 = 106.05 ; 2.5 * 2 = 5.0 ; diff = 101.05
        assertThat(room.area()).isEqualByComparingTo(new BigDecimal("101.05"));
    }

    @Test
    void construction_GivenCutoutEqualOnLength_ShouldThrow() {
        RectangularRoom cutout = new RectangularRoom(RoomDimension.of("20"), RoomDimension.of("4"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LShapedRoom(ANY_OUTER, cutout))
                .withMessage(CUTOUT_TOO_BIG_MSG);
    }

    @Test
    void construction_GivenCutoutEqualOnWidth_ShouldThrow() {
        RectangularRoom cutout = new RectangularRoom(RoomDimension.of("5"), RoomDimension.of("15"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LShapedRoom(ANY_OUTER, cutout))
                .withMessage(CUTOUT_TOO_BIG_MSG);
    }

    @Test
    void construction_GivenCutoutEqualOnBothAxes_ShouldThrow() {
        RectangularRoom cutout = new RectangularRoom(RoomDimension.of("20"), RoomDimension.of("15"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LShapedRoom(ANY_OUTER, cutout))
                .withMessage(CUTOUT_TOO_BIG_MSG);
    }

    @Test
    void construction_GivenCutoutLargerOnLength_ShouldThrow() {
        RectangularRoom cutout = new RectangularRoom(RoomDimension.of("21"), RoomDimension.of("4"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LShapedRoom(ANY_OUTER, cutout))
                .withMessage(CUTOUT_TOO_BIG_MSG);
    }

    @Test
    void construction_GivenCutoutLargerOnWidth_ShouldThrow() {
        RectangularRoom cutout = new RectangularRoom(RoomDimension.of("5"), RoomDimension.of("16"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new LShapedRoom(ANY_OUTER, cutout))
                .withMessage(CUTOUT_TOO_BIG_MSG);
    }

    @Test
    void estimate_GivenLShapedRoom_ShouldRoundGallonsUp() {
        // outer 20*15 = 300 ; cutout 5*4 = 20 ; area = 280 ; 280/350 -> ceil 1
        PaintEstimate estimate = PaintEstimator.estimate(new LShapedRoom(ANY_OUTER, ANY_CUTOUT));

        assertThat(estimate.area()).isEqualTo("280");
        assertThat(estimate.gallons()).isEqualTo(BigInteger.ONE);
    }

    @Test
    void estimate_GivenLargeLShapedRoom_ShouldRequireMultipleGallons() {
        // outer 50*30 = 1500 ; cutout 10*10 = 100 ; area = 1400 ; 1400/350 = 4 exact
        RoomDimensions room = new LShapedRoom(
                new RectangularRoom(RoomDimension.of("50"), RoomDimension.of("30")),
                new RectangularRoom(RoomDimension.of("10"), RoomDimension.of("10")));

        PaintEstimate estimate = PaintEstimator.estimate(room);

        assertThat(estimate.area()).isEqualTo("1400");
        assertThat(estimate.gallons()).isEqualTo(BigInteger.valueOf(4));
    }
}
