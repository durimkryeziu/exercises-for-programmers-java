package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

final class PaintEstimate {

    private final BigDecimal area;

    private final BigInteger gallons;

    PaintEstimate(BigDecimal area, BigInteger gallons) {
        this.area = Objects.requireNonNull(area, "area must not be null");
        this.gallons = Objects.requireNonNull(gallons, "gallons must not be null");
    }

    BigInteger gallons() {
        return this.gallons;
    }

    String area() {
        return this.area.stripTrailingZeros().toPlainString();
    }

    String gallonWord() {
        if (BigInteger.ONE.equals(this.gallons)) {
            return "gallon";
        }
        return "gallons";
    }
}
