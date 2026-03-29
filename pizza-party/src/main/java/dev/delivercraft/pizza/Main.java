package dev.delivercraft.pizza;

public final class Main {

    void main() {
        LineReader lineReader = new InputStreamLineReader(System.in);
        LineWriter lineWriter = new OutputStreamLineWriter(System.out);
        PizzaParty pizzaParty = new PizzaParty(lineReader, lineWriter);
        pizzaParty.displayPizzaParty();
    }
}
