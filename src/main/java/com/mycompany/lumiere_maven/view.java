/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.List;
import java.util.Scanner;
/**
 *
 * @author arch_kang
 */
public class view {
    public static void viewTasks(List<Task> tasks, Scanner input)
    {
        boolean status = true;
        while (status)
        {
            lines();
            System.out.println("All tasks:");
            viewAllTasks(tasks);
            System.out.println("""
                           Action:
                           1. Sort tasks.
                           2. Exit.""");
            System.out.print("> ");
            int action = input.nextInt();
            input.nextLine();
            switch (action) {
                case 1:
                    sort.sortTasks(tasks, input);
                    break;
                case 2:
                    System.out.println("Returning to main page...\n");
                    status = false;
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }
    
    public static void viewAllTasks(List<Task> tasks) 
    {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.printf("%d: %s [%s]\tDue: %s\n%s\nCategory: %s \tPriority: "
                    + "%s \nDepends on: %s \nRecurrence: %s\n\n", 
                    i + 1,
                    task.getTitle(),
                    (task.getStatus()) ? "completed" : "incomplete",
                    task.getDateStr(),
                    task.getDescription(),
                    task.getCategory(),
                    task.getPriority(),
                    task.getDependsStr(),
                    task.getRecurrenceInterval()
            );
        }
    }
    
    public static void lines()
    {
        for (int i = 0; i < 60; i++)
        {
            System.out.print("=");
        }
        System.out.println();
    }
}
