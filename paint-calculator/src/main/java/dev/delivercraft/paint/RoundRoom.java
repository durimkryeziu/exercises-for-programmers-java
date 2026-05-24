package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.util.Objects;

public record RoundRoom(RoomDimension radius) implements RoomDimensions {

    public RoundRoom(RoomDimension radius) {
        this.radius = Objects.requireNonNull(radius, "radius must not be null");
    }

    @Override
    public BigDecimal area() {
        return this.radius.value()
                .multiply(this.radius.value())
                .multiply(BigDecimal.valueOf(Math.PI));
    }
}
