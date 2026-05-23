package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.util.Objects;

record RoomDimensions(RoomDimension length, RoomDimension width) {

    RoomDimensions {
        Objects.requireNonNull(length, "length must not be null");
        Objects.requireNonNull(width, "width must not be null");
    }

    BigDecimal area() {
        return this.length.value().multiply(this.width.value());
    }
}
