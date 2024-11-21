/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lumiere;

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
    {
        for (int i = 0; i < tasks.size(); i++)
        {
            Task task = tasks.get(i);
            System.out.printf("%d: %s [%s]\n", i+1, task.title, 
                        (task.status)?"completed":"incomplete");
        }     
    }
    
    
    public static void main(String[] args) 
    {
        List<Task> tasks = getTasks();
        
        // menu goes here: 
        // will be written at last after all methods are ready
   
        
        
        
       
    }
    
}
