package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.util.Objects;

public record RectangularRoom(RoomDimension length, RoomDimension width) implements RoomDimensions {

    public RectangularRoom(RoomDimension length, RoomDimension width) {
        this.length = Objects.requireNonNull(length, "length must not be null");
        this.width = Objects.requireNonNull(width, "width must not be null");
    }

    @Override
    public BigDecimal area() {
        return this.length.value().multiply(this.width.value());
    }
}
