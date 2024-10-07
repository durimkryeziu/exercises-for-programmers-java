package com.craftsmanshipinsoftware.madlib;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MadLibTest {

  @ParameterizedTest(name = "Given input [{0}]")
  @ValueSource(strings = {"", "dog", "dog\nwalk", "dog\nwalk\nblue"})
  void printStory_GivenInputIsMissing_ShouldAskForMissingInput(String input) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    MadLib madLib = new MadLib(new ByteArrayInputStream(input.getBytes()), new PrintStream(outputStream));
    madLib.printStory();
    assertThat(outputStream).hasToString("Please enter something as input!" + System.lineSeparator());
  }

  @Test
  void printStory_GivenAllNeededInput_ShouldPrintStory() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    MadLib madLib = new MadLib(new ByteArrayInputStream("dog\nwalk\nblue\nquickly".getBytes()), new PrintStream(outputStream));
    madLib.printStory();
    assertThat(outputStream).hasToString("Do you walk your blue dog quickly? That's hilarious!" + System.lineSeparator());
  }

}
