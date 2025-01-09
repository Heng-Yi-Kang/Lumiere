/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.Scanner;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
 
public class EditTask {
    
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