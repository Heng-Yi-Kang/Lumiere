package com.mycompany.lumiere_maven;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LumiereApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI elements
        Label label = new Label("Welcome to Lumiere!");

        // Set up the root layout
        StackPane root = new StackPane();
        root.getChildren().add(label);

        // Create a scene and set it on the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Lumiere Task Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Start the JavaFX application
    }
}
