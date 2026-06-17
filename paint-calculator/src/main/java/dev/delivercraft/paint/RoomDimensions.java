package dev.delivercraft.paint;

import java.math.BigDecimal;

sealed interface RoomDimensions permits RectangularRoom, RoundRoom, LShapedRoom {

    BigDecimal area();
}
