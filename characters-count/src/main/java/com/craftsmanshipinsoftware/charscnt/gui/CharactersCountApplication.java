package com.craftsmanshipinsoftware.charscnt.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CharactersCountApplication extends Application {

  @Override
  public void start(Stage stage) {
    CharactersCountViewModel viewModel = new CharactersCountViewModel();
    CharactersCountView view = new CharactersCountView(viewModel);
    Scene scene = new Scene(view, 400, 200);
    stage.setTitle("Counting the Number of Characters");
    stage.setScene(scene);
    stage.show();
  }

}
