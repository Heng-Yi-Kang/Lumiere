/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author arch_kang
 */
public class loadData {
    private static final String file_path = "./resource/tasks.csv";
    private static Scanner input = new Scanner(System.in);

    // method to get all information from csv file, 
    // create an object of Task for each task,
    // and store them in a list called tasks.
    public static List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(file_path);
            CSVReader reader = new CSVReader(filereader);

            String[] nextRow;
            while ((nextRow = reader.readNext()) != null) {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(nextRow[3]);
                Task task = new Task(Integer.parseInt(nextRow[0]), nextRow[1],
                        nextRow[2], dueDate,
                        nextRow[4], nextRow[5], Boolean.parseBoolean(nextRow[6]),
                        nextRow[7], nextRow[8], Boolean.parseBoolean(nextRow[9]));
                tasks.add(task);
            }
            Dependency.generateDependency(tasks);
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // method to write all task information to csv file
    public static void saveTasks(List<Task> tasks) {
        File file = new File(file_path);

        try {
            FileWriter outputfile = new FileWriter(file, false);
            CSVWriter writer = new CSVWriter(outputfile);

            for (Task task : tasks) {
                String[] data
                        = {
                            Integer.toString(task.getId()),
                            task.getTitle(),
                            task.getDescription(),
                            task.getDateStr(),
                            task.getCategory(),
                            task.getPriority(),
                            Boolean.toString(task.getStatus()),
                            Dependency.saveDependency(task),
                            task.getRecurrenceInterval(),
                            Boolean.toString(task.getEmailStatus())
                        };
                writer.writeNext(data);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getUsername()
    {
        Properties prop = new Properties();
        String username = "";
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            username = prop.getProperty("username");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return username;
    }
    
    public static String getEmail() {
        Properties prop = new Properties();
        String email = "";
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            email = prop.getProperty("email");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return email;
    }
    
    public static String getToken() {
        Properties prop = new Properties();
        String token = "";
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            token = prop.getProperty("api_token");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return token;
    }
    
    public static void setUsername(String name)
    {
        Properties prop = new Properties();
        
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText(e.getMessage());
        }
        
        prop.setProperty("username", name);
        
        try (FileOutputStream output = new FileOutputStream("config.properties")){
            prop.store(output, "Updated username");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText(e.getMessage());
        }
    }
    
    public static void setEmail (String email)
    {
        Properties prop = new Properties();
        
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText(e.getMessage());
        }
        
        prop.setProperty("email", email);
        
        try (FileOutputStream output = new FileOutputStream("config.properties")){
            prop.store(output, "Updated email");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText(e.getMessage());
        }
    }
    
    public static void setToken(String token) {
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText(e.getMessage());
        }

        prop.setProperty("api_token", token);

        try (FileOutputStream output = new FileOutputStream("config.properties")) {
            prop.store(output, "Updated API token");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText(e.getMessage());
        }
    }
//    public static void initialise(Scanner input)
//    {   
//        String username = "";
//        String email = "";
//        String token = "";
//        Properties prop = new Properties();
//        try (FileInputStream reader = new FileInputStream("config.properties")) {
//            prop.load(reader);
//            username = prop.getProperty("username");
//        } catch (FileNotFoundException e) {
//            System.out.println("""
//                               ~~~ Welcome to Lumiere ~~~
//                               First timer? No worries - We'll guide you through ><""");
//            System.out.print("Enter your username: ");
//            username = input.nextLine();
//            
//            System.out.print("Enter your email: ");
//            email = input.nextLine();
//            
//            System.out.println("Lumiere is using Hungging Face API for some services. Please create an API token (read), your data would be stored locally.");
//            System.out.print("Enter your Hugging Face API token: ");
//            token = input.nextLine();
//            
//            System.out.println("\nYou can always edit your personal details in \"config.properties\"");
//            try 
//            {
//                PrintWriter writer = new PrintWriter(new FileOutputStream("config.properties", false));
//                writer.println("username=" + username);
//                writer.println("email=" + email);
//                writer.println("api_token=" + token);
//                writer.close();
//            }
//            catch(Exception ex){
//                System.out.println("Error while creating file");
//            }
//        } catch (IOException e){
//            System.out.println("Error occured");
//        }
//    }
    
    public static void initialise(Stage stage, List<Task> tasks) {
        
        Properties prop = new Properties();
        try (FileInputStream reader = new FileInputStream("config.properties")) {
            prop.load(reader);
            stage.setScene(Lumiere.mainMenu(stage, tasks));
        } catch (FileNotFoundException e) {
            stage.setScene(getInfo(stage, tasks));
        } catch (IOException e) {
            System.out.println("Error occured");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred!");
            alert.setContentText("Something went wrong. Please try again.");

            alert.showAndWait();
        }
        
    }
    
    public static Scene getInfo(Stage stage, List<Task> tasks)
    {
        String welcomeMessage = """
                               ~~~ Welcome to Lumiere ~~~
                               First timer? No worries - We'll guide you through ><""";

        Label welcome = new Label(welcomeMessage);
        welcome.getStyleClass().add("subheader");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your username");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        String apiMessage = "Lumiere is using Hungging Face API for some services. Please create an API token (read), your data would be stored locally.";
        Label api = new Label(apiMessage);
        api.getStyleClass().add("subheader");
        TextField apiField = new TextField();
        apiField.setPromptText("Enter your Hugging Face API token");

        Button submit = new Button("Submit");
        submit.setOnAction(event -> {
            try {
                PrintWriter writer = new PrintWriter(new FileOutputStream("config.properties", false));
                writer.println("username=" + nameField.getText());
                writer.println("email=" + emailField.getText());
                writer.println("api_token=" + apiField.getText());
                writer.close();
                stage.setScene(Lumiere.mainMenu(stage, tasks));
            } catch (Exception ex) {
                System.out.println("Error while creating file");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error has occurred!");
                alert.setContentText("Something went wrong. Please try again.");

                alert.showAndWait();
            }

        });
        
        VBox root = new VBox(10);
        root.getChildren().addAll(welcome, nameField, emailField, api, apiField, submit);
        root.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(Lumiere.class.getResource("/lumiere.css").toExternalForm());

        return scene;
    }
    
}
