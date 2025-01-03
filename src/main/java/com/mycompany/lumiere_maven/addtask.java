package com.mycompany.lumiere_maven;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class addtask {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

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
                // Create a recurring task
                Task recurringTask = new Task(
                        title,
                        description,
                        due_date,
                        category,
                        priority,
                        false, // Task is initially incomplete
                        dependency,
                        interval
                );

                tasks.add(recurringTask);
                System.out.println("Recurring task '" + title + "' added successfully!");

            } catch (Exception e) {
                System.out.println("Invalid date format. Task not added.");
            }
        } else {
            try {
                Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(due_date);
                // Create a non-recurring task
                Task newTask = new Task(
                        title,
                        description,
                        due_date,
                        category,
                        priority,
                        false, // Task is initially incomplete
                        dependency,
                        ""
                );

                tasks.add(newTask);
                System.out.println("Task '" + title + "' added successfully!");

            } catch (Exception e) {
                System.out.println("Invalid date format. Task not added.");
            }
        }
    }
}
