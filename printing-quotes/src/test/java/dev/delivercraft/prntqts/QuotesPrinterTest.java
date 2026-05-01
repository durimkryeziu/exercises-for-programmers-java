package dev.delivercraft.prntqts;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuotesPrinterTest {

    @Test
    void displayQuote_GivenNoQuote_ShouldAskToEnterTheQuote() {
        LineWriter lineWriter = new CapturingLineWriter();
        QuotesPrinter quotesPrinter = new QuotesPrinter(new StubLineReader(), lineWriter);

        quotesPrinter.displayQuote();

        assertThat(lineWriter.toString()).contains("Please enter the quote!" + System.lineSeparator());
    }

    @Test
    void displayQuote_GivenQuoteWithoutAuthor_ShouldAskToEnterTheAuthor() {
        LineWriter lineWriter = new CapturingLineWriter();
        QuotesPrinter quotesPrinter = new QuotesPrinter(new StubLineReader("To be or not to be"), lineWriter);

        quotesPrinter.displayQuote();

        assertThat(lineWriter.toString()).contains("Please enter the author!" + System.lineSeparator());
    }

    @Test
    void displayQuote_GivenQuoteAndAuthor_ShouldPrintQuoteWithAuthor() {
        LineWriter lineWriter = new CapturingLineWriter();
        QuotesPrinter quotesPrinter = new QuotesPrinter(
                new StubLineReader("To be or not to be", "William Shakespeare"), lineWriter);

        quotesPrinter.displayQuote();

        assertThat(lineWriter.toString())
                .contains("William Shakespeare says, \"To be or not to be\"" + System.lineSeparator());
    }

}
