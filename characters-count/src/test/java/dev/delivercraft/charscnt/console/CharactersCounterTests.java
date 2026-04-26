package dev.delivercraft.charscnt.console;

import dev.delivercraft.io.CapturingLineWriter;
import dev.delivercraft.io.LineReader;
import dev.delivercraft.io.LineWriter;
import dev.delivercraft.io.StubLineReader;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CharactersCounterTests {

    @Test
    void displayCharactersCount_GivenNoInput_ShouldAskToInputSomething() {
        LineReader lineReader = new StubLineReader();
        LineWriter lineWriter = new CapturingLineWriter();
        CharactersCounter charactersCounter = new CharactersCounter(lineReader, lineWriter);

        charactersCounter.displayCharactersCount();

        assertThat(lineWriter).hasToString("What is the input string? Please enter something as input!"
                + System.lineSeparator());
    }

    @Test
    void displayCharactersCount_GivenValidInput_ShouldDisplayCharactersCount() {
        LineReader lineReader = new StubLineReader("Durim");
        LineWriter lineWriter = new CapturingLineWriter();
        CharactersCounter charactersCounter = new CharactersCounter(lineReader, lineWriter);

        charactersCounter.displayCharactersCount();

        assertThat(lineWriter).hasToString("What is the input string? Durim has 5 characters."
                + System.lineSeparator());
    }

}
