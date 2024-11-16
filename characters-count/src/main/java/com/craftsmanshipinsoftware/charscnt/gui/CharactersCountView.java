package com.craftsmanshipinsoftware.charscnt.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

final class CharactersCountView extends VBox {

    private final TextField inputTextField = new TextField();

    private final Label outputLabel = new Label();

    private final CharactersCountViewModel viewModel;

    CharactersCountView(CharactersCountViewModel viewModel) {
        this.viewModel = viewModel;
        createView();
        bindViewModel();
    }

    private void createView() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 20, 10, 20));
        this.getChildren().addAll(new Label("What is the input string?"), this.inputTextField, this.outputLabel);
    }

    private void bindViewModel() {
        this.inputTextField.textProperty().bindBidirectional(this.viewModel.inputProperty());
        this.outputLabel.textProperty().bind(this.viewModel.outputProperty());
        this.viewModel.inputProperty().addListener((obs, ov, nv) -> this.viewModel.countInputCharacters());
    }
}
