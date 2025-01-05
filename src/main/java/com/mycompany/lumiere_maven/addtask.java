package com.mycompany.lumiere_maven;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class addtask {

    public static void createTask(List<Task> tasks) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Add New Task ===");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter due date (YYYY-MM-DD): ");
        String due_date = scanner.nextLine().trim();

        System.out.print("Enter task category (Homework, Personal, Work): ");
        String category = scanner.nextLine();

        System.out.print("Enter priority level (Low, Medium, High): ");
        String priority = scanner.nextLine();

        System.out.print("Is this a recurring task? (yes/no): ");
        String recurringResponse = scanner.nextLine();
        
        if (recurringResponse.equalsIgnoreCase("yes")) {
            System.out.print("Enter recurrence interval (daily, weekly, monthly): ");
            String interval = scanner.nextLine().toLowerCase();

            try {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(due_date);
                String[] intervalList = {"daily", "weekly", "monthly"};
                boolean status = false;
                for (String s : intervalList)
                {
                    if (interval.compareTo(s)==0)
                    {
                        // Create a recurring task
                        int id = tasks.size()+1;
                        Task recurringTask = new Task(
                                id,
                                title,
                                description,
                                dueDate,
                                category,
                                priority,
                                false, // Task is initially incomplete
                                "",
                                interval
                        );

                        tasks.add(recurringTask);
                        System.out.println("Recurring task '" + title + "' added successfully!");
                        status = true;
                    }
                }
                if (!status) System.out.println("Invalid interval.");
            } catch (Exception e) {
                System.out.println("Invalid date format. Task not added.");
            }
        } else {
            try {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(due_date);
                // Create a non-recurring task
                int id = tasks.size()+1;
                Task newTask = new Task(
                        id,
                        title,
                        description,
                        dueDate,
                        category,
                        priority,
                        false, // Task is initially incomplete
                        "",
                        "none"
                );

                tasks.add(newTask);
                System.out.println("Task '" + title + "' added successfully!");

            } catch (Exception e) {
                System.out.println("Invalid date format. Task not added.");
            }
        }
    }
    
    public static void deleteTask(List<Task> tasks, Scanner input)
    {
        view.lines();
        System.out.println("Task Deletion");
        view.viewAllTasks(tasks);
        System.out.print("Enter task no. to delete: ");
        int taskNum = input.nextInt();
        input.nextLine();
        
        if (taskNum < 1 || taskNum > tasks.size())
        {
            System.out.println("Invalid task number.");
        }
        else
        {
            String title = tasks.get(taskNum-1).getTitle();
            tasks.remove(taskNum-1);
            System.out.printf("Task %s was removed successfully.\n", 
                    title);
        }
        
    }
}