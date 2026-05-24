package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.util.Objects;

sealed interface RoomDimensions {

    BigDecimal area();

    record RectangularRoom(RoomDimension length, RoomDimension width) implements RoomDimensions {
        public RectangularRoom(RoomDimension length, RoomDimension width) {
            this.length = Objects.requireNonNull(length, "length must not be null");
            this.width = Objects.requireNonNull(width, "width must not be null");
        }

        @Override
        public BigDecimal area() {
            return this.length.value().multiply(this.width.value());
        }
    }

    record RoundRoom(RoomDimension radius) implements RoomDimensions {
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
}