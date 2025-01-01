/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.*;

public class CompleteTask2 {
    private static void markTaskAsComplete(Scanner scanner) {
    viewTasks();

    System.out.print("\nEnter the task number to mark as complete: ");
    int taskNumber = scanner.nextInt() - 1;

    if (taskNumber >= 0 && taskNumber < taskList.size()) {
        Task task = taskList.get(taskNumber);

        if (task.isComplete()) {
            System.out.println("Task is already marked as complete.");
            return;
        }

        // For tasks with dependencies
        for (Task dependency : task.getDependencies()){
            if(!dependency.isComplete()){
                System.out.println("Warning: Task \"" + task.getTitle() + "\" cannot be marked as complete because it depends on \"" + dependency.getTitle() + "\". Please complete \"" + dependency.getTitle() + "\" first.");
                return;
            }
        }
        task.isComplete(true);
        System.out.println("Task '" + task.getTitle() + "' marked as complete!");

        // Handle recurring tasks
        if (task.getRecurrenceInterval() != null && task.getRemainingOccurrences() > 0) {
            Date nextDueDate = calculateNextDueDate(task.getDueDate(), task.getRecurrenceInterval());
            Task nextTask = new Task(task.getTitle(), task.getDescription(), nextDueDate, task.getCategory(), task.getPriority(), task.getRecurrenceInterval(), task.getRemainingOccurrences() - 1);
            taskList.add(nextTask);
            task.decrementOccurrences();

            System.out.println("Next occurrence added: " + nextTask);
        }
    } else {
        System.out.println("Invalid task number.");
    }
}

private static Date calculateNextDueDate(Date currentDate, String interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);

    switch (interval) {
        case "daily":
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            break;
        case "weekly":
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            break;
        case "monthly":
            calendar.add(Calendar.MONTH, 1);
            break;
        default:
            throw new IllegalArgumentException("Invalid recurrence interval: " + interval);
    }

    return calendar.getTime();
}
}
