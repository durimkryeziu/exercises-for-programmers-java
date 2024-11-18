package com.craftsmanshipinsoftware.retirement;

import java.time.Year;

public class Main {

    public static void main(String[] args) {
        RetirementCalculator calculator = new RetirementCalculator(System.in, System.out, Year::now);
        calculator.printYearsLeft();
    }
}
