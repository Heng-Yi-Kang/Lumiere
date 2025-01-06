/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

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
            System.out.println("Error with loading email: " + e.getMessage());
        }
        return username;
    }
    
}
