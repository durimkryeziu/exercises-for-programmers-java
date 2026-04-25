package dev.delivercraft.pizza;

import dev.delivercraft.io.InputStreamLineReader;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.OutputStreamLineWriter;

public final class Main {

    void main() {
        LineReader lineReader = new InputStreamLineReader(System.in);
        LineWriter lineWriter = new OutputStreamLineWriter(System.out);
        PizzaParty pizzaParty = new PizzaParty(lineReader, lineWriter);
        pizzaParty.displayPizzaParty();
    }
}
