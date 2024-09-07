package com.craftsmanshipinsoftware.charscnt.gui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CharactersCountTests {

  @NullAndEmptySource
  @ValueSource(strings = {" "})
  @ParameterizedTest(name = "Given input: [{0}]")
  void of_GivenInvalidInput_ShouldThrowException(String input) {
    assertThatIllegalArgumentException().isThrownBy(() -> CharactersCount.of(input)).withMessage("input must not be blank");
  }

  @Test
  void of_GivenValidInput_ShouldReturnCharactersCount() {
    String input = "Durim";

    CharactersCount charactersCount = CharactersCount.of(input);

    assertThat(charactersCount).hasToString("Durim has 5 characters.");
  }
}
