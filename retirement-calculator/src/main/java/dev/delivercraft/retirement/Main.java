package dev.delivercraft.retirement;

import dev.delivercraft.io.InputStreamLineReader;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.OutputStreamLineWriter;

import java.time.Year;

public final class Main {

    void main() {
        LineReader lineReader = new InputStreamLineReader(System.in);
        LineWriter lineWriter = new OutputStreamLineWriter(System.out);
        RetirementCalculator calculator = new RetirementCalculator(lineReader, lineWriter, Year::now);
        calculator.printYearsLeft();
    }
}
