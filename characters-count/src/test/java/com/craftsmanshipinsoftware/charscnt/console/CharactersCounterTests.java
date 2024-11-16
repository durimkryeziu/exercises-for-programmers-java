package com.craftsmanshipinsoftware.charscnt.console;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class CharactersCounterTests {

    @Test
    void displayCharactersCount_GivenNoInput_ShouldAskToInputSomething() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CharactersCounter charactersCounter = new CharactersCounter(new ByteArrayInputStream(new byte[]{}),
                new PrintStream(outputStream));

        charactersCounter.displayCharactersCount();

        assertThat(outputStream).hasToString("Please enter something as input!" + System.lineSeparator());
    }

    @Test
    void displayCharactersCount_GivenValidInput_ShouldDisplayCharactersCount() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CharactersCounter charactersCounter = new CharactersCounter(new ByteArrayInputStream("Durim".getBytes()),
                new PrintStream(outputStream));

        charactersCounter.displayCharactersCount();

        assertThat(outputStream).hasToString("Durim has 5 characters." + System.lineSeparator());
    }

}
