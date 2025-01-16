
package com.mycompany.lumiere_maven;

import java.net.URL;
import java.util.List;
//import java.util.Scanner;
//import javafx.animation.FadeTransition;
//import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import javafx.util.Duration;

public class Lumiere extends Application
{
    @Override
    public void start(Stage stage) {
        List<Task> tasks = loadData.getTasks();
        loadData.initialise(stage, tasks);
        stage.setTitle("Lumiere");
        stage.show();
    }

    public static Scene mainMenu(Stage stage, List<Task> tasks) {
        email.checkDeadlines(tasks);
        Label title = new Label("Lumiere");
        title.getStyleClass().add("header");
        
        Label subheader = new Label("Your guide to a brighter, more organized life");
        subheader.getStyleClass().add("subheader");
        
        Label username = new Label("Welcome, " + loadData.getUsername());
        username.getStyleClass().add("subheader");
        
        VBox viewBox = new VBox();
        viewBox.setStyle("-fx-alignment: center;");

        Label viewText = new Label("View Tasks");
        viewText.getStyleClass().add("title-label");
        viewBox.getChildren().add(viewText);
        viewBox.setOnMouseClicked(e -> {
            stage.setScene(view.viewScene(stage, tasks));
        });

        VBox create = new VBox();
        create.setStyle("-fx-alignment: center;");

        Label createText = new Label("Create Tasks");
        createText.getStyleClass().add("title-label");
        create.getChildren().add(createText);
        create.setOnMouseClicked(e -> {
            stage.setScene(addtask.createScene(stage, tasks));
        });
        
        VBox search = new VBox();
        search.setStyle("-fx-alignment: center;");

        Label searchText = new Label("Search Tasks");
        searchText.getStyleClass().add("title-label");
        search.getChildren().add(searchText);
        search.setOnMouseClicked(e -> {
            stage.setScene(searchEngine.searchScene(stage, tasks));
        });
        
        VBox analyse = new VBox();
        analyse.setStyle("-fx-alignment: center;");

        Label analyseText = new Label("Analyse Tasks");
        analyseText.getStyleClass().add("title-label");
        analyse.getChildren().add(analyseText);
        analyse.setOnMouseClicked(e -> {
            stage.setScene(com.mycompany.lumiere_maven.analyse.analyseScene(stage, tasks));
        });
        
        Label userInfo = new Label("User Info");
        userInfo.getStyleClass().add("title-label");
        userInfo.setOnMouseClicked(e -> { stage.setScene(personalInfo.getUserInfo(stage, tasks)); });
        
        Button exit = new Button("Save and Exit");
        exit.setOnAction(event -> {
            loadData.saveTasks(tasks);
            stage.close();
        });
        
        VBox root = new VBox(10);
        root.getChildren().addAll(title, subheader, username, viewBox, create, search, analyse, userInfo, exit);
        root.setAlignment(Pos.CENTER);
        stage.setTitle("Main Menu");
        
        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());
//        scene.getStylesheets().add(Lumiere.class.getResource("./lumiere.css").toExternalForm());
//        URL url = Lumiere.class.getResource("/lumiere.css");
//        if (url == null) {
//            System.out.println("Resource not found. Aborting.");
//            System.exit(-1);
//        }
//        String css = url.toExternalForm();

//        scene.getStylesheets().add(css);
        
        return scene;
    }
    
    public static void main(String[] args) 
    {
//        Scanner input = new Scanner(System.in);
//        List<Task> tasks = loadData.getTasks();
//        boolean status = true;
//        while(status)
//        {
//            view.lines();
//            loadData.initialise(input);
//            email.checkDeadlines(tasks);
//            System.out.printf("Welcome %s!\n", loadData.getUsername());
//            System.out.println("Lumiere – your guide to a brighter, more organized life.");
//            System.out.println("""
//                             Action:
//                             1. View tasks.
//                             2. Create new task.
//                             3. Complete a task.
//                             4. Edit a task
//                             5. Search for task(s).
//                             6. Delete a task.
//                             7. Analyse tasks.
//                             8. Save and Exit.""");
//            System.out.print("> ");
//            int action = input.nextInt();
//            input.nextLine();
//            switch(action)
//            {
//                case 1:
//                    view.viewTasks(tasks, input);
//                    break;
//                case 2:
//                    addtask.createTask(tasks);
//                    break;
//                case 3:
//                    CompleteTask2.markTaskAsComplete(input, tasks);
//                    break;
//                case 4:
//                    EditTask.editTask(tasks, input);
//                    break;
//                case 5:
//                    searchEngine.run(tasks, input);
//                    break;
//                case 6:
//                    addtask.deleteTask(tasks, input);
//                    break;
//                case 7:
//                    analyse.showCompletionStatus(tasks);
//                    analyse.showCompletionRate(tasks);
//                    analyse.showCategorizedTask(tasks);
//                    break;
//                case 8:
//                    System.out.println("See you again :)");
//                    status = false;
//                    break;
//            }
//        }
//        loadData.saveTasks(tasks);
        launch();
    }
    
}
