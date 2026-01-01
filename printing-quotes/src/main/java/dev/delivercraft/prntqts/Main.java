package dev.delivercraft.prntqts;

public final class Main {

    void main() {
        QuotesPrinter quotesPrinter = new QuotesPrinter(System.in, System.out);
        quotesPrinter.displayQuote();
    }
}
