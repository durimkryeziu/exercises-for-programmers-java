package dev.delivercraft.paint;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

record RoomDimension(BigDecimal value) {

    private static final Pattern PLAIN_DECIMAL = Pattern.compile("\\d+(\\.\\d+)?");

    RoomDimension {
        Objects.requireNonNull(value, "value must not be null");
        if (BigDecimal.ZERO.compareTo(value) >= 0) {
            throw new IllegalArgumentException("Please enter a positive number! Input: %s".formatted(value));
        }
        value = value.stripTrailingZeros();
    }

    static RoomDimension of(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input must not be empty!");
        }

        String trimmedInput = input.trim();
        if (!PLAIN_DECIMAL.matcher(trimmedInput).matches()) {
            throw new IllegalArgumentException("Please enter a valid number! Input: %s".formatted(trimmedInput));
        }

        BigDecimal number = new BigDecimal(trimmedInput);
        return new RoomDimension(number);
    }

    @Override
    public String toString() {
        return this.value.toPlainString();
    }
}
