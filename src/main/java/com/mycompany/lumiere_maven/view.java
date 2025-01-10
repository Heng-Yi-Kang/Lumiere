/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import static com.mycompany.lumiere_maven.Lumiere.mainMenu;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class view {
    public static Scene viewScene(Stage stage, List<Task> tasks)
    {
        Label l = new Label("View Tasks");
        
        // Create a TableView
        TableView<Task> tableView = new TableView<>();

        // Create columns
        TableColumn<Task, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        TableColumn<Task, String> dateColumn = new TableColumn<>("Due Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("DateStr"));

        TableColumn<Task, Boolean> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        TableColumn<Task, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        
        TableColumn<Task, String> priorityColumn = new TableColumn<>("Priority");
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        
        TableColumn<Task, String> dependsColumn = new TableColumn<>("Depends On");
        dependsColumn.setCellValueFactory(new PropertyValueFactory<>("DependsStr"));
        
        TableColumn<Task, String> intervalColumn = new TableColumn<>("Repeat");
        intervalColumn.setCellValueFactory(new PropertyValueFactory<>("RecurrenceInterval"));
        
        statusColumn.setCellFactory(column -> {
            return new TableCell<Task, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setStyle(""); // Clear any styling
                    } else {
                        // Convert boolean to human-readable string
                        String statusText = item ? "Completed" : "Incomplete";
                        setText(statusText);

                        // Apply custom styling (e.g., color based on status)
                        if (item) {
                            setTextFill(Color.GREEN); // Green text for "Completed"
                        } else {
                            setTextFill(Color.RED); // Red text for "Incomplete"
                        }
                    }
                }
            };
        });
        
        priorityColumn.setComparator((priority1, priority2) -> {
            // Define the custom sorting order
            int priority1Value = sort.getPriorityValue(priority1);
            int priority2Value = sort.getPriorityValue(priority2);
            return Integer.compare(priority1Value, priority2Value);
        });

        // Add columns to the TableView
        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(descriptionColumn);
        tableView.getColumns().add(dateColumn);
        tableView.getColumns().add(statusColumn);
        tableView.getColumns().add(categoryColumn);
        tableView.getColumns().add(priorityColumn);
        tableView.getColumns().add(dependsColumn);
        tableView.getColumns().add(intervalColumn);

        // Create an ObservableList to hold the tasks
        ObservableList<Task> taskTable = FXCollections.observableArrayList(tasks);
        
        // Handle double-click events on rows
        tableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Task> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Task selectedTask = row.getItem();
                    stage.setScene(EditTask.showTaskDetails(selectedTask, stage, tasks));
                }
            });
            return row;
        });

        // Add tasks to the TableView
        tableView.setItems(taskTable);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(50);
        tableView.setMaxWidth(1500);
        
        Button b = new Button("Main Menu");
        b.setOnAction(e -> { stage.setScene(mainMenu(stage));});
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, tableView, b);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1200, 1000);
        tableView.prefHeightProperty().bind(scene.heightProperty().divide(1.33));
        tableView.prefWidthProperty().bind(scene.widthProperty().divide(1.33));
        
        return scene;
    }
    
    public static void viewTasks(List<Task> tasks, Scanner input)
    {
        boolean status = true;
        while (status)
        {
            lines();
            System.out.println("All tasks:");
            viewAllTasks(tasks);
            System.out.println("""
                           Action:
                           1. Sort tasks.
                           2. Exit.""");
            System.out.print("> ");
            int action = input.nextInt();
            input.nextLine();
            switch (action) {
                case 1:
                    sort.sortTasks(tasks, input);
                    break;
                case 2:
                    System.out.println("Returning to main page...\n");
                    status = false;
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }
    
    public static void viewAllTasks(List<Task> tasks) 
    {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.printf("%d: %s [%s]\tDue: %s\n%s\nCategory: %s \tPriority: "
                    + "%s \nDepends on: %s \nRecurrence: %s\n\n", 
                    i + 1,
                    task.getTitle(),
                    (task.getStatus()) ? "completed" : "incomplete",
                    task.getDateStr(),
                    task.getDescription(),
                    task.getCategory(),
                    task.getPriority(),
                    task.getDependsStr(),
                    task.getRecurrenceInterval()
            );
        }
    }
    
    public static void lines()
    {
        for (int i = 0; i < 60; i++)
        {
            System.out.print("=");
        }
        System.out.println();
    }
}
