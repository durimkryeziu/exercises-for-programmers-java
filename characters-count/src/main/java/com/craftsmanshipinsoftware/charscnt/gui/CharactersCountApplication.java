package com.craftsmanshipinsoftware.charscnt.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class CharactersCountApplication extends Application {

    private static final int WIDTH = 400;

    private static final int HEIGHT = 200;

    @Override
    public void start(Stage stage) {
        CharactersCountViewModel viewModel = new CharactersCountViewModel();
        CharactersCountView view = new CharactersCountView(viewModel);
        Scene scene = new Scene(view, WIDTH, HEIGHT);
        stage.setTitle("Counting the Number of Characters");
        stage.setScene(scene);
        stage.show();
    }

}
