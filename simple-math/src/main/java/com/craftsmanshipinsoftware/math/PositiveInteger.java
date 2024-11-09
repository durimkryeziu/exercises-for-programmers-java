package com.craftsmanshipinsoftware.math;

import java.io.Serial;

final class PositiveInteger extends Number {

  @Serial
  private static final long serialVersionUID = -4429661748302289322L;

  private final Integer value;

  private PositiveInteger(Integer value) {
    this.value = value;
  }

  static PositiveInteger of(String value) {
    try {
      int number = Integer.parseInt(value);
      if (number < 0) {
        throw new IllegalArgumentException("Please enter a positive number! Input: %d".formatted(number));
      }
      return new PositiveInteger(number);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Please enter a valid number! Input: %s".formatted(value), e);
    }
  }

  @Override
  public int intValue() {
    return this.value;
  }

  @Override
  public long longValue() {
    return this.value.longValue();
  }

  @Override
  public float floatValue() {
    return this.value.floatValue();
  }

  @Override
  public double doubleValue() {
    return this.value.doubleValue();
  }

  @Override
  public int hashCode() {
    return this.value.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof PositiveInteger that)) {
      return false;
    }
    return this.value.equals(that.value);
  }

  @Override
  public String toString() {
    return this.value.toString();
  }
}
