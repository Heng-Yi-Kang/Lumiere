/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lumiere_maven;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import java.util.Scanner;

/**
 *
 * @author arch_kang
 */
public class Lumiere 
{
    private static final String file_path = "./resource/tasks.csv";
    private static Scanner input = new Scanner(System.in);
    
    // method to get all information from csv file, 
    // create an object of Task for each task,
    // and store them in a list called tasks.
    public static List<Task> getTasks()
    {
        List<Task> tasks = new ArrayList<>();
        try 
        {
            FileReader filereader = new FileReader(file_path);
            CSVReader csvReader = new CSVReader(filereader);
            
            String [] nextRow;
            while ((nextRow = csvReader.readNext()) != null)
            {
                Task task = new Task(nextRow[0], nextRow[1], nextRow[2], 
                        nextRow[3], nextRow[4], Boolean.parseBoolean(nextRow[5]),
                        nextRow[6], nextRow[7]);
                tasks.add(task);
            }
          
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return tasks;
    }
    
    
    // method to write all task information to csv file
    public static void saveTasks(List<Task> tasks)
    {
        File file = new File(file_path);
        
        try 
        {
            FileWriter outputfile = new FileWriter(file, false);
            CSVWriter writer = new CSVWriter(outputfile);
                
            for (Task task : tasks)
            {
                String [] data = 
                {
                    task.title, 
                    task.description, 
                    task.due_date, 
                    task.category, 
                    task.priority, 
                    Boolean.toString(task.status),
                    task.depends_on,
                    task.repeat
                };
                writer.writeNext(data);
            }
               
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    
    // sample viewTasks(), print out a list of tasks
    public static void viewTasks(List<Task> tasks)
     { System.out.println("=== Task List ===");
    for (int i = 0; i < tasks.size(); i++) {
        Task task = tasks.get(i);
        System.out.printf("%d: %s [%s] (Due: %s, Priority: %s)\n", 
                          i + 1, task.title, 
                          (task.status ? "completed" : "incomplete"), 
                          task.due_date, task.priority);
    }
}
    
    public static void sortTasks(List<Task> tasks) {
    Scanner input = new Scanner(System.in);

    System.out.println("=== Sort Tasks ===");
    System.out.println("Sort by:");
    System.out.println("1. Due Date (Ascending)");
    System.out.println("2. Due Date (Descending)");
    System.out.println("3. Priority (High to Low)");
    System.out.println("4. Priority (Low to High)");
    System.out.print("> ");

    int choice = input.nextInt();

    switch (choice) {
        case 1:
            tasks.sort(Comparator.comparing(t -> t.due_date));
            System.out.println("Tasks sorted by Due Date (Ascending)!");
            break;
        case 2:
            tasks.sort((t1, t2) -> t2.due_date.compareTo(t1.due_date));
            System.out.println("Tasks sorted by Due Date (Descending)!");
            break;
        case 3:
            tasks.sort((t1, t2) -> t2.priority.compareTo(t1.priority));
            System.out.println("Tasks sorted by Priority (High to Low)!");
            break;
        case 4:
            tasks.sort(Comparator.comparing(t -> t.priority));
            System.out.println("Tasks sorted by Priority (Low to High)!");
            break;
        default:
            System.out.println("Invalid option. No sorting applied.");
            return;
            
    }
    viewTasks(tasks);
    }
    
    public static void main(String[] args) 
    {
        List<Task> tasks = getTasks();
        viewTasks(tasks);
        System.out.println("");
        sortTasks(tasks);
    
        // menu goes here: 
        // will be written at last after all methods are ready
   
    }
    
}