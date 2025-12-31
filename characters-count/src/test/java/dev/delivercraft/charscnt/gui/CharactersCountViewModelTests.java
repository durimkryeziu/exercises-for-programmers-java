package dev.delivercraft.charscnt.gui;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CharactersCountViewModelTests {

    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @ParameterizedTest(name = "Given input: [{0}]")
    void countInputCharacters_GivenInputIsEmpty_ShouldOutputPleaseEnterSomeInputString(String input) {
        CharactersCountViewModel viewModel = new CharactersCountViewModel();
        viewModel.inputProperty().setValue(input);

        viewModel.countInputCharacters();

        assertThat(viewModel.outputProperty().getValue()).isEqualTo("Please enter some input string.");
    }

    @Test
    void countInputCharacters_GivenInputIsHelloWorld_ShouldOutputCharacterCount() {
        CharactersCountViewModel viewModel = new CharactersCountViewModel();
        viewModel.inputProperty().setValue("Hello, World!");

        viewModel.countInputCharacters();

        assertThat(viewModel.outputProperty().getValue()).isEqualTo("Hello, World! has 13 characters.");
    }

}
