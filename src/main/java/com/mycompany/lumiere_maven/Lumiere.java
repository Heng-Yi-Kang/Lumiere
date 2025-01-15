
package com.mycompany.lumiere_maven;

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
        Label title = new Label("Lumiere");
        Label username = new Label("Welcome, " + loadData.getUsername());
        
        VBox viewBox = new VBox();
        viewBox.setStyle("-fx-alignment: center;");

        Label viewText = new Label("View Tasks");
        viewBox.getChildren().add(viewText);
        viewBox.setOnMouseClicked(e -> {
            stage.setScene(view.viewScene(stage, tasks));
        });

        VBox create = new VBox();
        create.setStyle("-fx-alignment: center;");

        Label createText = new Label("Create Tasks");
        create.getChildren().add(createText);
        create.setOnMouseClicked(e -> {
            stage.setScene(addtask.createScene(stage, tasks));
        });

        Button exit = new Button("Save and Exit");
        exit.setOnAction(event -> {
            loadData.saveTasks(tasks);
            stage.close();
        });
        
        VBox root = new VBox(10);
        root.getChildren().addAll(title, username, viewBox, create, exit);
        root.setAlignment(Pos.CENTER);
        stage.setTitle("Main Menu");
        
        return new Scene(root, 1200, 1000);
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
