package dev.delivercraft.retirement;

import java.time.Year;

public final class Main {

    void main() {
        RetirementCalculator calculator = new RetirementCalculator(System.in, System.out, Year::now);
        calculator.printYearsLeft();
    }
}
