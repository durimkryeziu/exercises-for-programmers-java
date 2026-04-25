package dev.delivercraft.pizza;

import dev.delivercraft.io.InputStreamLineReader;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.OutputStreamLineWriter;

public final class PizzaPurchasePlannerMain {

    void main() {
        LineReader lineReader = new InputStreamLineReader(System.in);
        LineWriter lineWriter = new OutputStreamLineWriter(System.out);
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(lineReader, lineWriter);
        planner.displayPizzaPurchasePlan();
    }
}
