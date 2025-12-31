package dev.delivercraft.prntqts;

public class Main {

    public static void main(String[] args) {
        QuotesPrinter quotesPrinter = new QuotesPrinter(System.in, System.out);
        quotesPrinter.displayQuote();
    }
}
