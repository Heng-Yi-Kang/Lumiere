
package com.mycompany.lumiere_maven;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(nextRow[3]);
                Task task = new Task(Integer.parseInt(nextRow[0]), nextRow[1], 
                        nextRow[2], dueDate, 
                        nextRow[4], nextRow[5], Boolean.parseBoolean(nextRow[6]),
                        nextRow[7], nextRow[8]);
                tasks.add(task);
            }
            Dependency.generateDependency(tasks);
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
                    Integer.toString(task.getId()),
                    task.getTitle(), 
                    task.getDescription(), 
                    task.getDateStr(), 
                    task.getCategory(), 
                    task.getPriority(), 
                    Boolean.toString(task.getStatus()),
                    Dependency.saveDependency(task),
                    task.getRecurrenceInterval()
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
    
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        List<Task> tasks = getTasks();
        boolean status = true;
        while(status)
        {
            view.lines();
            System.out.println("Lumiere – your guide to a brighter, more organized life.");
            System.out.println("""
                             Action:
                             1. View tasks.
                             2. Create new task.
                             3. Complete a task.
                             4. Edit a task
                             5. Search for task(s).
                             6. Delete a task.
                             7. Save and Exit.""");
            System.out.print("> ");
            int action = input.nextInt();
            input.nextLine();
            switch(action)
            {
                case 1:
                    view.viewTasks(tasks, input);
                    break;
                case 2:
                    addtask.createTask(tasks);
                    break;
                case 3:
                    CompleteTask2.markTaskAsComplete(input, tasks);
                    break;
                case 4:
                    EditTask.editTask(tasks, input);
                    break;
                case 5:
                    searchEngine.run(tasks, input);
                    break;
                case 6:
                    addtask.deleteTask(tasks, input);
                    break;
                case 7:
                    System.out.println("See you again :)");
                    status = false;
                    break;
                
            }
        }
        
        
        
        
//        viewTasks(tasks);
//        sort.sortTasks(tasks, input);
        
//        addtask.createTask(tasks);
//        viewTasks(tasks);
//        System.out.print("Enter task no. to edit: ");
//        int n = input.nextInt();
//        EditTask.editTask(tasks, n, input);
//        viewTasks(tasks);
//        saveTasks(tasks);
//        email.checkDeadlines(tasks);

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
