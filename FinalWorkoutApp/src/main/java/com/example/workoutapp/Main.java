package com.example.workoutapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Workout Tracker Program");
        stage.setScene(scene);
        stage.show();

        // Get the controller instance after the FXML is loaded
        Controller controller = fxmlLoader.getController();
        controller.initializeApp();

    }

    public static void main(String[] args) {
        launch();
    }
}