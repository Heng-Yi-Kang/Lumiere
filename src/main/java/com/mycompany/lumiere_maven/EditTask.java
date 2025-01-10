/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import static com.mycompany.lumiere_maven.view.viewScene;
import java.util.Scanner;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
public class EditTask {
    
    public static Scene showTaskDetails(Task task, Stage stage, List<Task> tasks) {
        Label title = new Label("Lumiere");
        
        Button titleEdit = new Button("Edit");
        titleEdit.setOnAction(e -> { editTitle(task); });
        
        Button desEdit = new Button("Edit");
        desEdit.setOnAction(e -> { editDescription(task); });
        
        Button dateEdit = new Button("Edit");
        dateEdit.setOnAction(e -> { editDate(task); });
        
        Button catEdit = new Button("Edit");
        catEdit.setOnAction(e -> { editCategory(task); });
        
        Button priorityEdit = new Button("Edit");
        priorityEdit.setOnAction(e -> { editPriority(task); });
        
        Button dependsEdit = new Button("Edit");
        dependsEdit.setOnAction(e -> { editDependency(tasks, task); });
        
        Button recurrenceEdit = new Button("Edit");
        
        GridPane grid = new GridPane();
        int i = 0;
        grid.add(new Label("Title"), 0, i);
        grid.add(new Label(":  " + task.getTitle()), 1, i);
        grid.add(titleEdit, 2, i);
        
        i++;
        grid.add(new Label("Description"), 0, i);
        grid.add(new Label(":  " + task.getDescription()), 1, i);
        grid.add(desEdit, 2, i);
        
        i++;
        grid.add(new Label("Due Date"), 0, i);
        grid.add(new Label(":  " + task.getDateStr()), 1, i);
        grid.add(dateEdit, 2, i);
        
        i++;
        grid.add(new Label("Category"), 0, i);
        grid.add(new Label(":  " + task.getCategory()), 1, i);
        grid.add(catEdit, 2, i);
        
        i++;
        grid.add(new Label("Priority"), 0, i);
        grid.add(new Label(":  " + task.getPriority()), 1, i);
        grid.add(priorityEdit, 2, i);
        
        i++;
        grid.add(new Label("Depends On"), 0, i);
        grid.add(new Label(":  " + task.getDependsStr()), 1, i);
        grid.add(dependsEdit, 2, i);
        
        i++;
        grid.add(new Label("Repeat"), 0, i);
        grid.add(new Label(":  " + task.getRecurrenceInterval()), 1, i);
        grid.add(recurrenceEdit, 2, i);
        
        grid.setHgap(10);
        grid.setVgap(10);
        
        Button back = new Button("Back");
        back.setOnAction(e -> {
            stage.setScene(viewScene(stage, tasks));
        });

        VBox root = new VBox(10);
//        root.getChildren().addAll(title, desBox, dateBox, catBox, priorityBox, 
//                dependsBox, recurrenceBox, back);
        root.getChildren().addAll(title, grid, back);

        return new Scene(root, 1200, 1000);
    }
    
    public static void editTitle(Task task)
    {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        
        Label l = new Label("Enter new title: ");
        Label result = new Label();
        TextField textField = new TextField();
        
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String input = textField.getText(); // Get the input from the TextField
            task.setTitle(input);
            result.setText("Title changed!");
        });
        
        Button close = new Button("close");
        close.setOnAction(event -> popup.close()); // Close the pop-up when clicked
        
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, close);
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);
        
        Scene s = new Scene(root, 500, 300);
        popup.setScene(s);
        popup.setTitle("Edit Title");
        popup.setResizable(false);
        
        popup.showAndWait();
    }
    
    public static void editDescription(Task task)
    {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        
        Label l = new Label("Enter new description: ");
        Label result = new Label();
        TextField textField = new TextField();
        
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String input = textField.getText(); // Get the input from the TextField
            task.setDescription(input);
            result.setText("Description changed!");
        });
        
        Button close = new Button("close");
        close.setOnAction(event -> popup.close()); // Close the pop-up when clicked
        
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, close);
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);
        
        Scene s = new Scene(root, 500, 300);
        popup.setScene(s);
        popup.setTitle("Edit Description");
        popup.setResizable(false);
        
        popup.showAndWait();
    }
    
    public static void editDate(Task task)
    {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        
        Label l = new Label("Enter new due date: ");
        Label result = new Label();
        TextField textField = new TextField();
        
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String input = textField.getText(); // Get the input from the TextField
            try {
                Date newDueDate = new SimpleDateFormat("yyyy-MM-dd").parse(input);
                task.setDueDate(newDueDate);
                result.setText("Task due date updated.");
            } catch (Exception e) {
                result.setText("Invalid date format!");
            }
        });
        
        Button close = new Button("close");
        close.setOnAction(event -> popup.close()); // Close the pop-up when clicked
        
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, close);
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);
        
        Scene s = new Scene(root, 500, 300);
        popup.setScene(s);
        popup.setTitle("Edit Due Date");
        popup.setResizable(false);
        
        popup.showAndWait();
    }
    
    public static void editCategory(Task task)
    {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        
        Label l = new Label("Enter new category: ");
        Label result = new Label();
        TextField textField = new TextField();
        
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String input = textField.getText(); // Get the input from the TextField
            task.setCategory(input);
            result.setText("Category changed!");
        });
        
        Button close = new Button("close");
        close.setOnAction(event -> popup.close()); // Close the pop-up when clicked
        
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, close);
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);
        
        Scene s = new Scene(root, 500, 300);
        popup.setScene(s);
        popup.setTitle("Edit Category");
        popup.setResizable(false);
        
        popup.showAndWait();
    }
    
    public static void editPriority(Task task)
    {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        
        Label l = new Label("Enter new priority: ");
        Label result = new Label();
        TextField textField = new TextField();
        
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String input = textField.getText(); // Get the input from the TextField
            task.setPriority(input);
            result.setText("priority changed!");
        });
        
        Button close = new Button("close");
        close.setOnAction(event -> popup.close()); // Close the pop-up when clicked
        
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, close);
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);
        
        Scene s = new Scene(root, 500, 300);
        popup.setScene(s);
        popup.setTitle("Edit Priority");
        popup.setResizable(false);
        
        popup.showAndWait();
    }
    public static void editDependency(List<Task> tasks, Task task)
    {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);

        popup.setScene(dependsMenu(popup, tasks, task));
        popup.setTitle("Edit Description");
        popup.setResizable(false);
        
        popup.showAndWait();
    }
    
    public static Scene dependsMenu(Stage stage, List<Task> tasks, Task task)
    {
        VBox root = new VBox(10);

        Label l = new Label("Current Dependencies ");
        root.getChildren().add(l);
        List<Task> dependencies = task.getDependsOn();
        if (dependencies.isEmpty()) {
            Label current = new Label("No dependencies.");
            root.getChildren().add(current);
        } else {
            for (int i = 0; i < dependencies.size(); i++) {
                Label current = new Label(String.format("%d. %s\n", i + 1, dependencies.get(i).getTitle()));
                root.getChildren().add(current);
            }
        }

        Button add = new Button("Add new");
        add.setOnAction(e -> {
            stage.setScene(addDepends(stage, tasks, task));
        });

        Button close = new Button("close");
        close.setOnAction(event -> stage.close()); // Close the pop-up when clicked

//        HBox buttons = new HBox(20);
//        buttons.setAlignment(Pos.CENTER);
//        buttons.getChildren().addAll(close);
        root.getChildren().addAll(add, close);
        
        return new Scene(root, 500, 300);
    }
    
    public static Scene addDepends(Stage stage, List<Task> tasks, Task task)
    {
        Label l = new Label("Available tasks:");
        Map<String, Integer> map = new LinkedHashMap<>();
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            String s = String.format("%d. %s\n", i + 1, t.getTitle());
            titles.add(s);
            map.put(s, i);
        }
        
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(titles);
        
        Label selectedLabel = new Label();
        Label selected = new Label();
        
        listView.setItems(items);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedLabel.setText("Selected: " + newValue);
            selected.setText(newValue);
        });
        
        Button confirm = new Button("Add");
        Label result = new Label();
        confirm.setOnAction(e -> { 
            Task dependentTask = tasks.get(task.getId() - 1);
            Task dependencyTask = tasks.get(map.get(selected.getText()));
            
            if (dependentTask == dependencyTask) result.setText("Error: A task cannot depend on itself.");
            else if (Dependency.isDependencyCycle(dependencyTask, dependentTask)) result.setText("Error: Adding this dependency creates a cycle.");
            else result.setText(dependentTask.addDependency(dependencyTask));
        });
        
        
        Button close = new Button("close");
        close.setOnAction(event -> stage.setScene(dependsMenu(stage, tasks, task)));
        
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, listView, selectedLabel, confirm, result, close);
        
        return new Scene(root, 500, 300);
    }
    
    public static void editTask(List<Task> tasks, Scanner sc){
        view.lines();
        System.out.println("Task Edit");
        view.viewAllTasks(tasks);
        System.out.print("Enter task number to edit: ");
        int taskNumber = sc.nextInt();
        sc.nextLine();
        
        if (taskNumber<1 || taskNumber>tasks.size()){
            System.out.println("Invalid task number");
            return;
        }
        
        Dependency dependency = new Dependency();
        Task task = tasks.get(taskNumber-1);
        System.out.printf("\nTask: %s", task.getTitle());
        while(true){
            System.out.println();
            System.out.print("""
                               What would you like to edit?
                               1. Title
                               2. Description
                               3. Due Date
                               4. Category
                               5. Priority 
                               6. Manage Task Dependency
                               7. Recurrence Interval
                               8. Back
                               > """);
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice){
                case 1:{
                    System.out.print("Enter new title: ");
                    String newTitle = sc.nextLine();
                    
                    task.setTitle(newTitle);
                    System.out.println("Task title updated.");
                    break;}
                
                case 2:{
                    System.out.print("Enter new description: ");
                    String newDescription = sc.nextLine();
                    task.setDescription(newDescription);
                    System.out.println("Task description updated.");
                    break;}
                    
                case 3:{
                    System.out.print("Enter new due date: ");
                    String newDate = sc.nextLine();
                    try{
                        Date newDueDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
                        task.setDueDate(newDueDate);
                        System.out.println("Task due date updated.");
                    } catch (Exception e){
                        System.out.println("Invalid date format.");
                    }
                    break;}
                    
                case 4:{
                    System.out.print("Enter new category: ");
                    String newCategory = sc.nextLine();
                    task.setCategory(newCategory);
                    System.out.println("Task category updated.");
                    break;}
                    
                case 5:{
                    System.out.print("Set priority: ");
                    String newPriority = sc.nextLine();
                    task.setPriority(newPriority);
                    System.out.println("Task priority updated.");
                    break;}
                    
                case 6:
                {
                    dependency.manageDependencies(tasks, task, sc);
                    break;
                }
                    
                case 7:
                {
                    System.out.printf("Current recurrence interval: %s\n", task.getRecurrenceInterval());
                    System.out.print("Enter new recurrence interval (none, daily, weekly, monthly): ");
                    String interval = sc.nextLine().toLowerCase();
                    String[] intervalList = {"none", "daily", "weekly", "monthly"};
                    for (String s : intervalList)
                    {
                        if (interval.compareTo(s)==0) 
                        {
                            task.setRecurrenceInterval(interval);
                            System.out.println("Recurrence interval changed successfully.");
                            break;
                        }
                    }
                    if (interval.compareTo(task.getRecurrenceInterval())!=0)
                        System.out.println("Invalid interval.");
                    break;
                }
                case 8:
                    return;
                    
                default:
                    System.out.println("Invalid choice.");
            }
            
            
        }
    }

}