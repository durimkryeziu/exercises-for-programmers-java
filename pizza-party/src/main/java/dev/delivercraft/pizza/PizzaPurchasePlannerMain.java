package dev.delivercraft.pizza;

public final class PizzaPurchasePlannerMain {

    void main() {
        LineReader lineReader = new InputStreamLineReader(System.in);
        LineWriter lineWriter = new OutputStreamLineWriter(System.out);
        PizzaPurchasePlanner planner = new PizzaPurchasePlanner(lineReader, lineWriter);
        planner.displayPizzaPurchasePlan();
    }
}
