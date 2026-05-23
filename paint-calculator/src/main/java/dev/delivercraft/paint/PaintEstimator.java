package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

final class PaintEstimator {

    private static final int SQUARE_FEET_PER_GALLON = 350;

    private PaintEstimator() {
    }

    static PaintEstimate estimate(RoomDimensions dimensions) {
        Objects.requireNonNull(dimensions, "dimensions must not be null");
        BigDecimal area = dimensions.area();
        BigInteger gallons = area.divide(BigDecimal.valueOf(SQUARE_FEET_PER_GALLON), 0, RoundingMode.CEILING)
                .toBigIntegerExact();
        return new PaintEstimate(area, gallons);
    }
}
