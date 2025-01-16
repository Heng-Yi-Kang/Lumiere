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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditTask {
    public static Scene showTaskDetails(Stage stage, List<Task> tasks, Task task) {
        Label title = new Label("Task Details");
        String s = String.format("[%s]", task.getStatus() ? "Completed" : "Incomplete");
        Label status = new Label(s);

        HBox header = new HBox(30);
        header.getChildren().addAll(title, status);

        Button titleEdit = new Button("Edit");
        titleEdit.setOnAction(e -> stage.setScene(editTitle(stage, tasks, task)));

        Button desEdit = new Button("Edit");
        desEdit.setOnAction(e -> stage.setScene(editDescription(stage, tasks, task)));

        Button dateEdit = new Button("Edit");
        dateEdit.setOnAction(e -> stage.setScene(editDate(stage, tasks, task)));

        Button catEdit = new Button("Edit");
        catEdit.setOnAction(e -> stage.setScene(editCategory(stage, tasks, task)));

        Button priorityEdit = new Button("Edit");
        priorityEdit.setOnAction(e -> stage.setScene(editPriority(stage, tasks, task)));

        Button dependsEdit = new Button("Edit");
        dependsEdit.setOnAction(e -> stage.setScene(editDependency(stage, tasks, task)));

        Button recurrenceEdit = new Button("Edit");
        recurrenceEdit.setOnAction(e -> stage.setScene(editRecurrence(stage, tasks, task)));

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

        Button delete = new Button("Delete");
        delete.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Task Management");
            alert.setHeaderText("Delete Task - " + task.getTitle());
            alert.setContentText("Are you sure you want to proceed?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Remove the task from the dependency lists of all other tasks
                    removeTaskFromDependencies(tasks, task);

                    // Delete the task
                    tasks.remove(task);
                    stage.setScene(viewScene(stage, tasks));
                }
            });
        });

        HBox buttons = new HBox(20);
        buttons.getChildren().addAll(back, delete);

        CheckBox mark = new CheckBox("Mark as Complete");
        mark.setSelected(task.getStatus());
        Label stateLabel = new Label();
        mark.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Check if all dependencies are completed
                boolean allDependenciesCompleted = true;
                for (Task dependency : task.getDependsOn()) {
                    if (!dependency.getStatus()) {
                        allDependenciesCompleted = false;
                        break;
                    }
                }

                if (allDependenciesCompleted) {
                    task.setStatus(true);
                    stateLabel.setText("Task marked as complete.");
                } else {
                    mark.setSelected(false); // Revert the checkbox
                    stateLabel.setText("Cannot mark as complete: Some dependencies are incomplete.");
                }
            } else {
                task.setStatus(false);
                stateLabel.setText("Task marked as incomplete.");
            }
            stage.setScene(showTaskDetails(stage, tasks, task));
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(header, grid, mark, stateLabel, buttons);
        stage.setTitle(task.getTitle());

        return new Scene(root, 1200, 1000);
    }

    public static void removeTaskFromDependencies(List<Task> tasks, Task taskToDelete) {
        for (Task task : tasks) {
            List<Task> dependencies = task.getDependsOn();
            dependencies.removeIf(dependency -> dependency.equals(taskToDelete));
        }
    }

    public static Scene editTitle(Stage stage, List<Task> tasks, Task task) {
        Label l = new Label("Enter new title: ");
        Label result = new Label();
        TextField textField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String input = textField.getText(); // Get the input from the TextField
            task.setTitle(input);
            result.setText("Title changed!");
        });

        Button back = new Button("Back");
        back.setOnAction(event -> stage.setScene(showTaskDetails(stage, tasks, task))); // Close the pop-up when clicked

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, back);

        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);

        stage.setTitle(task.getTitle() + "Edit Title");
        return new Scene(root, 1200, 1000);
    }

    public static Scene editDescription(Stage stage, List<Task> tasks, Task task)
    {
        Label l = new Label("Enter new description: ");
        Label result = new Label();
        TextField textField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String input = textField.getText();
            task.setDescription(input);
            result.setText("Description changed!");
        });

        Button back = new Button("Back");
        back.setOnAction(event -> stage.setScene(showTaskDetails(stage, tasks, task)));

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, back);

        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);

        stage.setTitle("Edit Description");
        return new Scene(root, 1200, 1000);
    }

    public static Scene editDate(Stage stage, List<Task> tasks, Task task)
    {
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
        close.setOnAction(event -> stage.setScene(showTaskDetails(stage, tasks, task))); // Close the pop-up when clicked

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, close);

        VBox root = new VBox(10);
        root.getChildren().addAll(l, textField, result, buttons);

        stage.setTitle("Edit Due Date");
        return new Scene(root, 1200, 1000);
    }

    public static Scene editCategory(Stage stage, List<Task> tasks, Task task)
    {
        Label l = new Label("Choose an option:");
        String[] titles = {"work", "homework", "personal"};

        ObservableList<String> items = FXCollections.observableArrayList(titles);

        ListView<String> listView = new ListView<>();
        listView.setItems(items);
        listView.setFixedCellSize(24);
        listView.setPrefHeight(listView.getFixedCellSize() * 3 + 2);

        Label selectedLabel = new Label();
        Label selected = new Label();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedLabel.setText("Selected: " + newValue);
            selected.setText(newValue);
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            task.setCategory(selected.getText());
            selectedLabel.setText("Category changed to " + selected.getText());
        });

        Button back = new Button("Back");
        back.setOnAction(event -> stage.setScene(showTaskDetails(stage, tasks, task)));

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, back);

        VBox root = new VBox(10);
        root.getChildren().addAll(l, listView, selectedLabel, buttons);

        stage.setTitle("Edit Category");
        return new Scene(root, 1200, 1000);
    }

    public static Scene editPriority(Stage stage, List<Task> tasks, Task task)
    {
        Label l = new Label("Choose an option:");
        String[] titles = {"low", "medium", "high"};

        ObservableList<String> items = FXCollections.observableArrayList(titles);

        ListView<String> listView = new ListView<>();
        listView.setItems(items);
        listView.setFixedCellSize(24);
        listView.setPrefHeight(listView.getFixedCellSize() * 3 + 2);

        Label selectedLabel = new Label();
        Label selected = new Label();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedLabel.setText("Selected: " + newValue);
            selected.setText(newValue);
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            task.setPriority(selected.getText());
            selectedLabel.setText("Priority changed to " + selected.getText());
        });

        Button back = new Button("Back");
        back.setOnAction(event -> stage.setScene(showTaskDetails(stage, tasks, task)));

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, back);

        VBox root = new VBox(10);
        root.getChildren().addAll(l, listView, selectedLabel, buttons);

        stage.setTitle("Edit Priority");
        return new Scene(root, 1200, 1000);
    }

    public static Scene editDependency(Stage stage,List<Task> tasks, Task task) {
        VBox root = new VBox(10);

        Label l = new Label("Current Dependencies ");
        root.getChildren().add(l);
        List<Task> dependencies = task.getDependsOn();
        if (dependencies.isEmpty()) {
            Label current = new Label("No dependencies.");
            root.getChildren().add(current);
        } else {
//            for (int i = 0; i < dependencies.size(); i++) {
//                Label current = new Label(String.format("%d. %s\n", i + 1, dependencies.get(i).getTitle()));
//                root.getChildren().add(current);
//            }
            TableView<Task> tableView = new TableView<>();

            TableColumn<Task, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            TableColumn<Task, String> dateColumn = new TableColumn<>("Due Date");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("DateStr"));
            TableColumn<Task, Boolean> statusColumn = new TableColumn<>("Status");
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            statusColumn.setCellFactory(column -> {
                return new TableCell<Task, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            String statusText = item ? "Completed" : "Incomplete";
                            setText(statusText);

                            if (item) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.RED);
                            }
                        }
                    }
                };
            });
            tableView.getColumns().add(titleColumn);
            tableView.getColumns().add(dateColumn);
            tableView.getColumns().add(statusColumn);
            ObservableList<Task> taskTable = FXCollections.observableArrayList(dependencies);

            tableView.setRowFactory(tv -> {
                javafx.scene.control.TableRow<Task> row = new javafx.scene.control.TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        Task selectedTask = row.getItem();
                        stage.setScene(EditTask.showTaskDetails(stage, tasks, selectedTask));
                    }
                });
                return row;
            });

            HBox display = new HBox(20);
            Label selectedLabel = new Label();
            Button delete = new Button("Delete");
            delete.setVisible(false);
            tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                selectedLabel.setText("Selected: " + newValue.getTitle());
                delete.setVisible(true);
                delete.setOnAction(event -> {
                    dependencies.remove(newValue);
                    selectedLabel.setText(newValue.getTitle() + " successfully removed from dependency list.");
                    delete.setVisible(false);
                });

            });

            tableView.setItems(taskTable);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setFixedCellSize(50);
            tableView.setMaxWidth(1500);
            tableView.setPrefHeight(dependencies.size() * 50 + 25);

            display.getChildren().addAll(selectedLabel, delete);
            root.getChildren().addAll(tableView, display);

        }

        Button add = new Button("Add new");
        add.setOnAction(e -> stage.setScene(addDepends(stage, tasks, task)));

        Button back = new Button("Back");
        back.setOnAction(event -> stage.setScene(showTaskDetails(stage, tasks, task)));

        root.getChildren().addAll(add, back);
        stage.setTitle(task.getTitle() + " - Manage Dependencies");

        return new Scene(root, 1200, 1000);
    }

    public static Scene addDepends(Stage stage, List<Task> tasks, Task task) {
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
            Task dependentTask = task;
            Task dependencyTask = tasks.get(map.get(selected.getText()));

            if (dependentTask == dependencyTask) {
                result.setText("Error: A task cannot depend on itself.");
            } else if (Dependency.isDependencyCycle(dependencyTask, dependentTask)) {
                result.setText("Error: Adding this dependency creates a cycle.");
            } else {
                result.setText(dependentTask.addDependency(dependencyTask));
            }
        });

        Button back = new Button("Back");
        back.setOnAction(event -> stage.setScene(editDependency(stage, tasks, task)));

        VBox root = new VBox(10);
        root.getChildren().addAll(l, listView, selectedLabel, confirm, result, back);

        return new Scene(root, 1200, 1000);
    }

    public static Scene editRecurrence(Stage stage, List<Task> tasks, Task task)
    {
        Label l = new Label("Choose an option:");
        String[] titles = {"none", "daily", "weekly", "monthly"};

        ObservableList<String> items = FXCollections.observableArrayList(titles);

        ListView<String> listView = new ListView<>();
        listView.setItems(items);
        listView.setFixedCellSize(24);
        listView.setPrefHeight(listView.getFixedCellSize() * 4 + 2);

        Label selectedLabel = new Label();
        Label selected = new Label();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedLabel.setText("Selected: " + newValue);
            selected.setText(newValue);
        });

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            task.setRecurrenceInterval(selected.getText());
            selectedLabel.setText("Recurrence interval changed to " + selected.getText());
        });

        Button back = new Button("Back");
        back.setOnAction(event -> stage.setScene(showTaskDetails(stage, tasks, task)));

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submitButton, back);

        VBox root = new VBox(10);
        root.getChildren().addAll(l, listView, selectedLabel, buttons);

        stage.setTitle("Edit Recurrence Interval");
        return new Scene(root, 1200, 1000);
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