/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author arch_kang
 */
public class personalInfo {
    public static Scene getUserInfo(Stage stage, List<Task> tasks)
    {
        Label title = new Label("User Info");
        title.getStyleClass().add("subheader");
        
        Button usernameEdit = new Button("Edit");
        usernameEdit.setOnAction(e -> { stage.setScene(editUsername(stage, tasks));});
        
        Button emailEdit = new Button("Edit");
        emailEdit.setOnAction(e -> { stage.setScene(editEmail(stage, tasks));});
        
        Button tokenEdit = new Button("Edit");
        tokenEdit.setOnAction(e -> { stage.setScene(editToken(stage, tasks));});
        
        GridPane grid = new GridPane();
        int i = 0;
        grid.add(new Label("Username"), 0, i);
        grid.add(new Label(": " + loadData.getUsername()), 1, i);
        grid.add(usernameEdit, 2, i);
        
        i++;
        grid.add(new Label("Email"), 0, i);
        grid.add(new Label(": " + loadData.getEmail()), 1, i);
        grid.add(emailEdit, 2, i);
        
        i++;
        grid.add(new Label("Hugging Face API token"), 0, i);
        grid.add(new Label(": " + loadData.getToken()), 1, i);
        grid.add(tokenEdit, 2, i);
        
        grid.setHgap(10);
        grid.setVgap(10);
        
        grid.setAlignment(Pos.CENTER);
        Button menu = new Button("Main Menu");
        menu.setOnAction(event -> { stage.setScene(Lumiere.mainMenu(stage, tasks));});
        
        VBox root = new VBox(10);
        root.getChildren().addAll(title, grid, menu);
        root.setAlignment(Pos.CENTER);
        stage.setTitle("User Info");
        
        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());

        return scene;
    }
    
    public static Scene editUsername(Stage stage, List<Task> tasks) {
        Label title = new Label("User Info");
        title.getStyleClass().add("subheader");

        TextField newUsername = new TextField();
        newUsername.setPromptText("Enter username");
        Button usernameEdit = new Button("confirm");
        usernameEdit.setOnAction(e -> {
            loadData.setUsername(newUsername.getText());
            stage.setScene(getUserInfo(stage, tasks));
        });
        
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> { stage.setScene(getUserInfo(stage, tasks)); });

        Button emailEdit = new Button("Edit");
        emailEdit.setOnAction(e -> {
            stage.setScene(editEmail(stage, tasks));
        });

        Button tokenEdit = new Button("Edit");
        tokenEdit.setOnAction(e -> {
            stage.setScene(editToken(stage, tasks));
        });

        GridPane grid = new GridPane();
        int i = 0;
        grid.add(new Label("Username"), 0, i);
        grid.add(newUsername, 1, i);
        grid.add(usernameEdit, 2, i);
        grid.add(cancel, 3, i);
        
        i++;
        grid.add(new Label("Email"), 0, i);
        grid.add(new Label(": " + loadData.getEmail()), 1, i);
        grid.add(emailEdit, 2, i);

        i++;
        grid.add(new Label("Hugging Face API token"), 0, i);
        grid.add(new Label(": " + loadData.getToken()), 1, i);
        grid.add(tokenEdit, 2, i);

        grid.setHgap(10);
        grid.setVgap(10);

        Button menu = new Button("Main Menu");
        menu.setOnAction(event -> {
            stage.setScene(Lumiere.mainMenu(stage, tasks));
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(title, grid, menu);
        
        grid.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());

        return scene;
    }
    
    public static Scene editEmail(Stage stage, List<Task> tasks) 
    {
        Label title = new Label("User Info");
        title.getStyleClass().add("subheader");

        Button usernameEdit = new Button("Edit");
        usernameEdit.setOnAction(e -> { stage.setScene(editUsername(stage, tasks));});
        
        TextField newEmail = new TextField();
        newEmail.setPromptText("Enter email");
        Button emailEdit = new Button("Confirm");
        emailEdit.setOnAction(e -> {
            loadData.setEmail(newEmail.getText());
            stage.setScene(getUserInfo(stage, tasks));
        });
        
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            stage.setScene(getUserInfo(stage, tasks));
        });

        Button tokenEdit = new Button("Edit");
        tokenEdit.setOnAction(e -> {
            stage.setScene(editToken(stage, tasks));
        });

        GridPane grid = new GridPane();
        int i = 0;
        grid.add(new Label("Username"), 0, i);
        grid.add(new Label(": " + loadData.getUsername()), 1, i);
        grid.add(usernameEdit, 2, i);

        i++;
        grid.add(new Label("Email"), 0, i);
        grid.add(newEmail, 1, i);
        grid.add(emailEdit, 2, i);
        grid.add(cancel, 3, i);

        i++;
        grid.add(new Label("Hugging Face API token"), 0, i);
        grid.add(new Label(": " + loadData.getToken()), 1, i);
        grid.add(tokenEdit, 2, i);

        grid.setHgap(10);
        grid.setVgap(10);

        Button menu = new Button("Main Menu");
        menu.setOnAction(event -> {
            stage.setScene(Lumiere.mainMenu(stage, tasks));
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(title, grid, menu);
        grid.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());

        return scene;
    }
    
    public static Scene editToken (Stage stage, List<Task> tasks) 
    {
        Label title = new Label("User Info");
        title.getStyleClass().add("subheader");

        Button usernameEdit = new Button("Edit");
        usernameEdit.setOnAction(e -> { stage.setScene(editUsername(stage, tasks));});
        
        Button emailEdit = new Button("Edit");
        emailEdit.setOnAction(e -> { stage.setScene(editEmail(stage, tasks));});
        
        TextField newToken = new TextField();
        newToken.setPromptText("Enter API token");
        Button tokenEdit = new Button("Confirm");
        tokenEdit.setOnAction(e -> { 
            loadData.setToken(newToken.getText());
            stage.setScene(getUserInfo(stage, tasks));
        });
        
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> {
            stage.setScene(getUserInfo(stage, tasks));
        });

        GridPane grid = new GridPane();
        int i = 0;
        grid.add(new Label("Username"), 0, i);
        grid.add(new Label(": " + loadData.getUsername()), 1, i);
        grid.add(usernameEdit, 2, i);

        i++;
        grid.add(new Label("Email"), 0, i);
        grid.add(new Label(": " + loadData.getEmail()), 1, i);
        grid.add(emailEdit, 2, i);

        i++;
        grid.add(new Label("Hugging Face API token"), 0, i);
        grid.add(newToken, 1, i);
        grid.add(tokenEdit, 2, i);
        grid.add(cancel, 3, i);

        grid.setHgap(10);
        grid.setVgap(10);

        Button menu = new Button("Main Menu");
        menu.setOnAction(event -> {
            stage.setScene(Lumiere.mainMenu(stage, tasks));
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(title, grid, menu);
        grid.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());

        return scene;
    }
    
    
}
