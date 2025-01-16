package com.mycompany.lumiere_maven;

import static com.mycompany.lumiere_maven.Lumiere.mainMenu;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class addtask {
    
    public static Scene createScene(Stage stage, List<Task> tasks)
    {
        Label l = new Label("Create Tasks");
        l.getStyleClass().add("subheader");

        TextField titleField = new TextField();
        titleField.setPromptText("Enter task title");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter task description");

        TextField dueDateField = new TextField();
        dueDateField.setPromptText("Enter due date (YYYY-MM-DD)");

        ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>();
        categoryChoiceBox.getItems().addAll("Homework", "Personal", "Work");
        categoryChoiceBox.setValue("Homework");

        ChoiceBox<String> priorityChoiceBox = new ChoiceBox<>();
        priorityChoiceBox.getItems().addAll("Low", "Medium", "High");
        priorityChoiceBox.setValue("Low");

        CheckBox recurringCheckBox = new CheckBox("Is this a recurring task?");
        TextField intervalField = new TextField();
        intervalField.setPromptText("Enter recurrence interval (daily, weekly, monthly)");
        intervalField.setDisable(true);

        recurringCheckBox.setOnAction(e -> intervalField.setDisable(!recurringCheckBox.isSelected()));

        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String dueDateStr = dueDateField.getText();
            String category = categoryChoiceBox.getValue();
            String priority = priorityChoiceBox.getValue();
            boolean isRecurring = recurringCheckBox.isSelected();
            String interval = intervalField.getText();

            try {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);
                Task newTask;

                if (isRecurring) {
                    newTask = new Task(tasks.size() + 1, title, description, dueDate, category, priority, false, "", interval, false);
                } else {
                    newTask = new Task(tasks.size() + 1, title, description, dueDate, category, priority, false, "", "none", false);
                }

                tasks.add(newTask);
                System.out.println("Task '" + title + "' added successfully!");
                stage.setScene(mainMenu(stage, tasks));
            } catch (Exception ex) {
                System.out.println("Invalid date format. Task not added.");
            }
        });

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> stage.setScene(mainMenu(stage, tasks)));

        VBox root = new VBox(10);
        root.getChildren().addAll(l, titleField, descriptionField, dueDateField, categoryChoiceBox, priorityChoiceBox, recurringCheckBox, intervalField, addButton, mainMenuButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());

        return scene;
    
    }

    public static void createTask(List<Task> tasks) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Add New Task ===");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String due_date = scanner.nextLine().trim();

        System.out.print("Enter task category (Homework, Personal, Work): ");
        String category = scanner.nextLine();

        System.out.print("Enter priority level (Low, Medium, High): ");
        String priority = scanner.nextLine();

        System.out.print("Is this a recurring task? (yes/no): ");
        String recurringResponse = scanner.nextLine();
        
        if (recurringResponse.equalsIgnoreCase("yes")) {
            System.out.print("Enter recurrence interval (daily, weekly, monthly): ");
            String interval = scanner.nextLine().toLowerCase();

            try {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(due_date);
                String[] intervalList = {"daily", "weekly", "monthly"};
                boolean status = false;
                for (String s : intervalList)
                {
                    if (interval.compareTo(s)==0)
                    {
                        // Create a recurring task
                        int id = tasks.size()+1;
                        Task recurringTask = new Task(
                                id,
                                title,
                                description,
                                dueDate,
                                category,
                                priority,
                                false, // Task is initially incomplete
                                "",
                                interval,
                                false
                        );

                        tasks.add(recurringTask);
                        System.out.println("Recurring task '" + title + "' added successfully!");
                        status = true;
                    }
                }
                if (!status) System.out.println("Invalid interval.");
            } catch (Exception e) {
                System.out.println("Invalid date format. Task not added.");
            }
        } else {
            try {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(due_date);
                // Create a non-recurring task
                int id = tasks.size()+1;
                Task newTask = new Task(
                        id,
                        title,
                        description,
                        dueDate,
                        category,
                        priority,
                        false, // Task is initially incomplete
                        "",
                        "none",
                        false
                );

                tasks.add(newTask);
                System.out.println("Task '" + title + "' added successfully!");

            } catch (Exception e) {
                System.out.println("Invalid date format. Task not added.");
            }
        }
    }
    
    public static void deleteTask(List<Task> tasks, Scanner input)
    {
        view.lines();
        System.out.println("Task Deletion");
        view.viewAllTasks(tasks);
        System.out.print("Enter task no. to delete: ");
        int taskNum = input.nextInt();
        input.nextLine();
        
        if (taskNum < 1 || taskNum > tasks.size())
        {
            System.out.println("Invalid task number.");
        }
        else
        {
            String title = tasks.get(taskNum-1).getTitle();
            tasks.remove(taskNum-1);
            System.out.printf("Task %s was removed successfully.\n", 
                    title);
        }
        
    }
}