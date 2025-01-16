package com.mycompany.lumiere_maven;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class analyse {

    public static void showCompletionStatus(List<Task> tasks) {
        int completedTasks = 0;
        int pendingTasks = 0;

        for (Task task : tasks) {
            if (task.getStatus()) {
                completedTasks++;
            } else {
                pendingTasks++;
            }
        }

        System.out.println("--- Task Completion Status ---");
        System.out.println("Completed Tasks: " + completedTasks);
        System.out.println("Pending Tasks: " + pendingTasks);
    }

    public static void showCompletionRate(List<Task> tasks) {
        int totalTasks = tasks.size();
        int completedTasks = 0;

        for (Task task : tasks) {
            if (task.getStatus()) {
                completedTasks++;
            }
        }

        double completionRate = (double) completedTasks / totalTasks * 100;
        System.out.println("\n --- Task Completion Rate ---");
        System.out.printf("Task Completion Rate: %.2f%%\n", completionRate);
    }

    public static void showCategorizedTask(List<Task> tasks) {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Task task : tasks) {
            String category = task.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        System.out.println("\n --- Categorized Task Count ---");
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " tasks");
        }
    }

    public static Scene analyseScene(Stage stage, List<Task> tasks) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Task Analysis");

        Button completionStatusButton = new Button("Show Completion Status");
        completionStatusButton.setOnAction(e -> stage.setScene(showCompletionStatusScene(stage, tasks)));

        Button completionRateButton = new Button("Show Completion Rate");
        completionRateButton.setOnAction(e -> stage.setScene(showCompletionRateScene(stage, tasks)));

        Button categorizedTaskButton = new Button("Show Categorized Task Count");
        categorizedTaskButton.setOnAction(e -> stage.setScene(showCategorizedTaskScene(stage, tasks)));

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> stage.setScene(Lumiere.mainMenu(stage, tasks)));

        root.getChildren().addAll(titleLabel, completionStatusButton, completionRateButton, categorizedTaskButton, mainMenuButton);

        return new Scene(root, 1200, 1000);
    }

    private static Scene showCompletionStatusScene(Stage stage, List<Task> tasks) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("--- Task Completion Status ---");
        int completedTasks = (int) tasks.stream().filter(Task::getStatus).count();
        int pendingTasks = tasks.size() - completedTasks;

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Status");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Tasks");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Task Completion Status");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Tasks");
        XYChart.Data<String, Number> completedData = new XYChart.Data<>("Completed", completedTasks);
        XYChart.Data<String, Number> pendingData = new XYChart.Data<>("Pending", pendingTasks);

        dataSeries.getData().add(completedData);
        dataSeries.getData().add(pendingData);

        barChart.getData().add(dataSeries);

        // Set colors and labels
        completedData.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setStyle("-fx-bar-fill: green;");
                newValue.setOnMouseEntered(event -> {
                    Tooltip.install(newValue, new Tooltip("Completed: " + completedTasks));
                });
            }
        });

        pendingData.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setStyle("-fx-bar-fill: red;");
                newValue.setOnMouseEntered(event -> {
                    Tooltip.install(newValue, new Tooltip("Pending: " + pendingTasks));
                });
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(analyseScene(stage, tasks)));

        root.getChildren().addAll(titleLabel, barChart, backButton);

        return new Scene(root, 1200, 1000);
    }

    private static Scene showCompletionRateScene(Stage stage, List<Task> tasks) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("\n --- Task Completion Rate ---");
        int completedTasks = (int) tasks.stream().filter(Task::getStatus).count();
        double completionRate = (double) completedTasks / tasks.size() * 100;

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Completed", completedTasks),
                new PieChart.Data("Pending", tasks.size() - completedTasks)
        );

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Task Completion Rate");

        // Set colors and labels
        pieChartData.forEach(data -> {
            String label = String.format("%s: %.2f%% (%d)", data.getName(), (data.getPieValue() / tasks.size()) * 100, (int) data.getPieValue());
            data.setName(label);
            if (data.getName().contains("Completed")) {
                data.getNode().setStyle("-fx-pie-color: green;");
            } else {
                data.getNode().setStyle("-fx-pie-color: red;");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(analyseScene(stage, tasks)));

        root.getChildren().addAll(titleLabel, pieChart, backButton);

        return new Scene(root, 1200, 1000);
    }

    private static Scene showCategorizedTaskScene(Stage stage, List<Task> tasks) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("\n --- Categorized Task Count ---");
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Task task : tasks) {
            String category = task.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Tasks");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Categorized Task Count");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Tasks");
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
            dataSeries.getData().add(data);

            // Set labels
            data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    newValue.setOnMouseEntered(event -> {
                        Tooltip.install(newValue, new Tooltip(entry.getKey() + ": " + entry.getValue() + " tasks"));
                    });
                }
            });
        }

        barChart.getData().add(dataSeries);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(analyseScene(stage, tasks)));

        root.getChildren().addAll(titleLabel, barChart, backButton);

        return new Scene(root, 1200, 1000);
    }
}