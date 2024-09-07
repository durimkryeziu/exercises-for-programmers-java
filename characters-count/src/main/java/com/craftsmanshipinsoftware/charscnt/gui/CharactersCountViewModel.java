package com.craftsmanshipinsoftware.charscnt.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

final class CharactersCountViewModel {

  private final StringProperty input = new SimpleStringProperty();

  private final StringProperty output = new SimpleStringProperty();

  StringProperty inputProperty() {
    return this.input;
  }

  StringProperty outputProperty() {
    return this.output;
  }

  void countInputCharacters() {
    String inputValue = this.input.getValue();
    if (inputValue == null || inputValue.isBlank()) {
      this.output.set("Please enter some input string.");
    } else {
      this.output.set(CharactersCount.of(inputValue).toString());
    }
  }

}
