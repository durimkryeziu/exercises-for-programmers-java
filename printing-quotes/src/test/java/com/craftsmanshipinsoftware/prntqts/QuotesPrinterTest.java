package com.craftsmanshipinsoftware.prntqts;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class QuotesPrinterTest {

    @Test
    void displayQuote_GivenNoQuote_ShouldAskToEnterTheQuote() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QuotesPrinter quotesPrinter = new QuotesPrinter(new ByteArrayInputStream(new byte[0]),
                new PrintStream(outputStream));

        quotesPrinter.displayQuote();

        assertThat(outputStream).hasToString("Please enter the quote!" + System.lineSeparator());
    }

    @Test
    void displayQuote_GivenQuoteWithoutAuthor_ShouldAskToEnterTheAuthor() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QuotesPrinter quotesPrinter = new QuotesPrinter(new ByteArrayInputStream("To be or not to be".getBytes()),
                new PrintStream(outputStream));

        quotesPrinter.displayQuote();

        assertThat(outputStream).hasToString("Please enter the author!" + System.lineSeparator());
    }

    @Test
    void displayQuote_GivenQuoteAndAuthor_ShouldPrintQuoteWithAuthor() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QuotesPrinter quotesPrinter = new QuotesPrinter(
                new ByteArrayInputStream("To be or not to be\nWilliam Shakespeare".getBytes()),
                new PrintStream(outputStream));

        quotesPrinter.displayQuote();

        assertThat(outputStream).hasToString(
                "William Shakespeare says, \"To be or not to be\"" + System.lineSeparator());
    }
}
