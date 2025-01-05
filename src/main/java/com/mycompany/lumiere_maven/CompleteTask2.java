/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.*;

public class CompleteTask2 {
    public static void markTaskAsComplete(Scanner scanner, List<Task> taskList) 
    {
        view.lines();
        for (int i = 0; i < taskList.size(); i++) 
        {
            Task task = taskList.get(i);
            System.out.printf("%2d. %50s [%s]\n", i + 1, task.getTitle(), task.getStatus() ? "Complete" : "Incomplete");
        }

    System.out.print("\nEnter the task number to mark as complete: ");
    int taskNumber = scanner.nextInt() - 1;
    scanner.nextLine();

    if (taskNumber >= 0 && taskNumber < taskList.size()) {
        Task task = taskList.get(taskNumber);

        if (task.getStatus()) {
            System.out.println("Task is already marked as complete.");
            return;
        }

        // For tasks with dependencies
        for (Task dependency : task.getDependsOn()){
            if(!dependency.getStatus()){
                System.out.println("Warning: Task \"" + task.getTitle() + "\" cannot be marked as complete because it depends on \"" + dependency.getTitle() + "\". Please complete \"" + dependency.getTitle() + "\" first.");
                return;
            }
        }
        task.setStatus(true);
        System.out.println("Task '" + task.getTitle() + "' marked as complete!");

        // Handle recurring tasks
//        if (task.getRecurrenceInterval() != null && task.getRemainingOccurrences() > 0) {
        if (task.getRecurrenceInterval().compareTo("none")!=0) 
        {
            Date nextDueDate = calculateNextDueDate(task.getDueDate(), task.getRecurrenceInterval());
            Task nextTask = new Task(
                    taskList.size(),
                    task.getTitle(), 
                    task.getDescription(), 
                    nextDueDate, 
                    task.getCategory(), 
                    task.getPriority(), 
                    false,
                    task.getDependsId(),
                    task.getRecurrenceInterval());
            taskList.add(nextTask);
            nextTask.setDependsOn(task.getDependsOn());
//            task.decrementOccurrences();

            System.out.println("Next occurrence added: ");
            System.out.printf("Title: %s\n", nextTask.getTitle());
            System.out.printf("Due: %s\n", nextTask.getDateStr());
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
