/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.*;
 
public class EditTask {
    
    private static List<Task> tasks = new ArrayList<>();
    

    public void editTask(int taskNumber, Scanner sc){
        if (taskNumber<1 || taskNumber>tasks.size()){
            System.out.println("Invalid task number");
            return;
        }
        
        Dependency dependency = new Dependency();
        Task task = tasks.get(taskNumber -1);
        
        while(true){
            System.out.println("""
                               What would you like to edit?
                               1. Title
                               2. Description
                               3. Due Date
                               4. Category
                               5. Priority 
                               6. Set Task Dependency
                               7. Cancel
                               """);
            System.out.println("> ");
            int choice = sc.nextInt();
            
            switch (choice){
                case 1:
                    System.out.print("Enter new title: ");
                    String newTitle = sc.nextLine();
                    task.setTitle(newTitle);
                    System.out.println("Task title updated.");
                
                case 2:
                    System.out.print("Enter new description: ");
                    String newDescription = sc.nextLine();
                    task.setDescription(newDescription);
                    System.out.println("Task description updated.");
                    
                case 3:
                    System.out.print("Enter new due date (YYYY-MM-DD): ");
                    String newDate = sc.nextLine();
                    try {
                        String[] parts = newDate.split("-");
                        if (parts.length == 3) {
                            int year = Integer.parseInt(parts[0]);
                            int month = Integer.parseInt(parts[1]) - 1;
                            int day = Integer.parseInt(parts[2]);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, day, 0, 0, 0);
                            Date dueDate = calendar.getTime();
                            task.setDueDate(dueDate);
                            System.out.println("Task due date updated.");
                        } else {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    }
                    
                case 4:
                    System.out.print("Enter new category: ");
                    String newCategory = sc.nextLine();
                    task.setCategory(newCategory);
                    System.out.println("Task category updated.");
                    
                case 5:
                    System.out.print("Set priority: ");
                    String newPriority = sc.nextLine();
                    task.setPriority(newPriority);
                    System.out.println("Task priority updated.");
                    
                case 6:
                    System.out.println("Tasks available: ");
                    viewTasks();
                    System.out.print("Enter task number that depends on another task: ");
                    int taskNumber1 = sc.nextInt();
                    System.out.print("Enter the task number it depends on: ");
                    int taskNumber2 = sc.nextInt();
                    dependency.addDependency(taskNumber1, taskNumber2);
                    break;
                    
                case 7:
                    return;
                    
                default:
                    System.out.println("Invalid choice.");
            }
            
            
        }
    }

}