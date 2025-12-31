package dev.delivercraft.charscnt.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

final class CharactersCountView extends VBox {

    private static final int SPACING_VALUE = 10;

    private static final int TOP = 10;

    private static final int RIGHT = 20;

    private static final int BOTTOM = 10;

    private static final int LEFT = 20;

    private final TextField inputTextField = new TextField();

    private final Label outputLabel = new Label();

    private final CharactersCountViewModel viewModel;

    CharactersCountView(CharactersCountViewModel viewModel) {
        this.viewModel = viewModel;
        createView();
        bindViewModel();
    }

    private void createView() {
        setSpacing(SPACING_VALUE);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(TOP, RIGHT, BOTTOM, LEFT));
        this.getChildren().addAll(new Label("What is the input string?"), this.inputTextField, this.outputLabel);
    }

    private void bindViewModel() {
        this.inputTextField.textProperty().bindBidirectional(this.viewModel.inputProperty());
        this.outputLabel.textProperty().bind(this.viewModel.outputProperty());
        this.viewModel.inputProperty().addListener((obs, ov, nv) -> this.viewModel.countInputCharacters());
    }
}
