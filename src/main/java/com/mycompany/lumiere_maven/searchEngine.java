package com.mycompany.lumiere_maven;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class searchEngine {

    private static void embedding(String query, List<Task> tasks, Stage stage) throws Exception {
        String apiUrl = "https://api-inference.huggingface.co/models/sentence-transformers/all-MiniLM-L6-v2";
        Properties prop = new Properties();
        String apiToken;
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            apiToken = prop.getProperty("api_token");
        }

        ArrayList<String> descriptionList = new ArrayList<>();
        for (Task t : tasks) {
            String input = String.format("\"%s\"", t.getDescription());
            descriptionList.add(input);
        }
        String allDescription = descriptionList.toString();

        String jsonInput = "{\"inputs\": { \"source_sentence\": \"" + query + "\", "
                + "\"sentences\": " + allDescription + " }}";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiToken);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String response = new String(connection.getInputStream().readAllBytes());
            output(response, tasks, stage);
        } else {
            System.out.println("Error: " + responseCode);
            String errorResponse = new String(connection.getErrorStream().readAllBytes());
            System.out.println("Error Response: " + errorResponse);
        }
    }

    private static void output(String response, List<Task> tasks, Stage stage) {
        String[] list = response.replaceAll("[\\[\\]]", "").split(",");
        double[] scores = new double[list.length];
        double n = 1000.0, sum = 0, avg;
        for (int i = 0; i < list.length; i++) {
            scores[i] = Math.round(Double.parseDouble(list[i]) * n) / n;
            sum += scores[i];
        }
        avg = sum / list.length;
        List<Task> filteredTasks = new ArrayList<>();
        for (int j = 0; j < scores.length; j++) {
            if (scores[j] >= avg) {
                filteredTasks.add(tasks.get(j));
            }
        }
        showResults(stage, filteredTasks, scores, avg, tasks);
    }

    private static void showResults(Stage stage, List<Task> filteredTasks, double[] scores, double avg, List<Task> originalTasks) {
        Label l = new Label("Search Results");

        TableView<Task> tableView = new TableView<>();
        ObservableList<Task> taskTable = FXCollections.observableArrayList(filteredTasks);

        TableColumn<Task, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Task, String> dateColumn = new TableColumn<>("Due Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("DateStr"));

        TableColumn<Task, Boolean> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setCellFactory(column -> new TableCell<Task, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    String statusText = item ? "Complete" : "Incomplete";
                    setText(statusText);
                    setTextFill(item ? Color.GREEN : Color.RED);
                }
            }
        });

        TableColumn<Task, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Task, Double> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(cellData -> {
            int index = filteredTasks.indexOf(cellData.getValue());
            return new javafx.beans.property.SimpleObjectProperty<>(scores[index]);
        });

        tableView.getColumns().addAll(titleColumn, descriptionColumn, dateColumn, statusColumn, categoryColumn, scoreColumn);
        tableView.setItems(taskTable);

        tableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Task> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Task selectedTask = row.getItem();
                    stage.setScene(EditTask.showTaskDetails(stage, originalTasks, selectedTask));
                }
            });
            return row;
        });

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(50);
        tableView.setMaxWidth(1500);
        tableView.setPrefHeight(filteredTasks.size() * 50 + 25);

        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> { stage.setScene(searchScene(stage, originalTasks)); });
        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> stage.setScene(Lumiere.mainMenu(stage, originalTasks)));

        HBox buttons = new HBox(20);
        buttons.getChildren().addAll(searchBtn, mainMenuButton);
        
        VBox root = new VBox(10);
        root.getChildren().addAll(l, tableView, buttons);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1200, 1000);
        
        stage.setTitle("Search Results");
        stage.setScene(scene);
    }

    public static Scene searchScene(Stage stage, List<Task> tasks) {
        Label l = new Label("Search Tasks");
        l.getStyleClass().add("subheader");

        TextField queryField = new TextField();
        queryField.setPromptText("Enter search keyword");
        queryField.setPrefWidth(500);
        

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String query = queryField.getText();
            try {
                embedding(query, tasks, stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        HBox search = new HBox(20);
        search.getChildren().addAll(queryField, searchButton);
        search.setAlignment(Pos.CENTER);

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> stage.setScene(Lumiere.mainMenu(stage, tasks)));

        VBox root = new VBox(10);
        root.getChildren().addAll(l, search, mainMenuButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());

        return scene;
    }
}
