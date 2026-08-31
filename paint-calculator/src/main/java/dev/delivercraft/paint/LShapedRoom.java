package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.util.Objects;

public record LShapedRoom(RectangularRoom outer, RectangularRoom cutout) implements RoomDimensions {

    public LShapedRoom(RectangularRoom outer, RectangularRoom cutout) {
        this.outer = Objects.requireNonNull(outer, "outer must not be null");
        this.cutout = Objects.requireNonNull(cutout, "cutout must not be null");

        BigDecimal outerLength = outer.length().value();
        BigDecimal outerWidth = outer.width().value();
        BigDecimal cutoutLength = cutout.length().value();
        BigDecimal cutoutWidth = cutout.width().value();

        if (cutoutLength.compareTo(outerLength) >= 0 || cutoutWidth.compareTo(outerWidth) >= 0) {
            throw new IllegalArgumentException(
                    "Cut-out must be smaller than the outer rectangle on both length and width.");
        }
    }

    @Override
    public BigDecimal area() {
        return this.outer.area().subtract(this.cutout.area());
    }
}
