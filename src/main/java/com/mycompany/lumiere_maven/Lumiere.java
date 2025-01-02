
package com.mycompany.lumiere_maven;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.util.Comparator;

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
            CSVReader reader = new CSVReader(filereader);
            
            String [] nextRow;
            while ((nextRow = reader.readNext()) != null)
            {
                Task task = new Task(nextRow[0], nextRow[1], nextRow[2], 
                        nextRow[3], nextRow[4], Boolean.parseBoolean(nextRow[5]),
                        nextRow[6], nextRow[7]);
                tasks.add(task);
            }

            reader.close();

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
                    task.getTitle(), 
                    task.getDescription(), 
                    task.getDueDate(), 
                    task.getCategory(), 
                    task.getPriority(), 
                    Boolean.toString(task.getStatus()),
                    task.getDepends(),
                    task.getRepeat()
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
    public static void viewTasks(List<Task> tasks) {
    System.out.println("=== Task List ===");
    System.out.printf("%-4s %-25s %-12s %-12s %-15s %-20s\n", "No.", "Title", "Status", "Due Date", "Priority", "Category");
    System.out.println("===========================================================================================");
    
    for (int i = 0; i < tasks.size(); i++) {
        Task task = tasks.get(i);
        String dueDate = task.getDueDate().split(" ")[0]; 
        
        System.out.printf("%-4d %-25s %-12s %-12s %-15s %-20s\n",
                i + 1,
                task.getTitle(),
                task.status ? "Completed" : "Incomplete",
                dueDate,
                task.getPriority(),
                task.getCategory());
    }
    
    System.out.println("===========================================================================================");
}
    
    public static void sortTasks(List<Task> tasks) {
    System.out.println("=== Sort Tasks ===");
    System.out.println("Sort by:");
    System.out.println("1. Due Date (Ascending)");
    System.out.println("2. Due Date (Descending)");
    System.out.println("3. Priority (High to Low)");
    System.out.println("4. Priority (Low to High)");
    System.out.print("> ");

    int choice = input.nextInt();
    input.nextLine(); 

    switch (choice) {
        case 1:
            tasks.sort(Comparator.comparing(Task::getDueDate));
            System.out.println("Tasks sorted by Due Date (Ascending)!");
            break;
        case 2:
            tasks.sort(Comparator.comparing(Task::getDueDate).reversed());
            System.out.println("Tasks sorted by Due Date (Descending)!");
            break;
        case 3:
        case 4:
            tasks.sort((task1, task2) -> {
                int priority1 = getPriorityValue(task1.getPriority());
                int priority2 = getPriorityValue(task2.getPriority());
                return choice == 3 ? Integer.compare(priority2, priority1) // High to Low
                                   : Integer.compare(priority1, priority2); // Low to High
            });
            System.out.println("Tasks sorted by Priority (" + (choice == 3 ? "High to Low" : "Low to High") + ")!");
            break;
        default:
            System.out.println("Invalid choice. No sorting applied.");
            return;
    }
    System.out.println("");
    viewTasks(tasks);}
    
    private static int getPriorityValue(String priority) {
    switch (priority.toLowerCase()) {
        case "high":
            return 3;
        case "medium":
            return 2;
        case "low":
            return 1;
        default:
            return 0;
    }
}
    
    public static void main(String[] args) 
    {
        List<Task> tasks = getTasks();
        viewTasks(tasks);
        System.out.println("");
        sortTasks(tasks);
        

        // vector search:
//        String query;
//        try
//        {
//            System.out.print("Search by keyword: ");
//            Scanner input = new Scanner(System.in);
//            query = input.nextLine();
//            searchEngine.embedding(query, tasks);
//        }
//        
//        catch(Exception e){e.printStackTrace();}

        // menu goes here: 
        // will be written at last after all methods are ready
   
    }
    
}
