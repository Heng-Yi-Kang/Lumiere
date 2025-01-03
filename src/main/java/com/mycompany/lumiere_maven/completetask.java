package com.mycompany.lumiere_maven;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.*;

public class completetask {

    private static void markTaskAsComplete(Scanner scanner, List<Task> taskList) {
        // Display tasks
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.printf("%d. %s [%s]\n", i + 1, task.getTitle(), task.getStatus() ? "Complete" : "Incomplete");
        }

        System.out.print("\nEnter the task number to mark as complete: ");
        int taskNumber = scanner.nextInt() - 1;

        if (taskNumber >= 0 && taskNumber < taskList.size()) {
            Task task = taskList.get(taskNumber);

            if (task.getStatus()) {
                System.out.println("Task is already marked as complete.");
                return;
            }

            // Check dependencies
            if (!task.getDepends().isEmpty()) {
                for (String dependencyTitle : task.getDepends().split(",")) {
                    Task dependency = taskList.stream()
                            .filter(t -> t.getTitle().equals(dependencyTitle))
                            .findFirst()
                            .orElse(null);

                    if (dependency != null && !dependency.getStatus()) {
                        System.out.println("Cannot mark task as complete. Dependency \"" + dependencyTitle + "\" is incomplete.");
                        return;
                    }
                }
            }

            // Mark task as complete
            task.setStatus(true);
            System.out.println("Task '" + task.getTitle() + "' marked as complete!");

            // Handle recurring tasks
            if (!task.getRepeat().isEmpty()) {
                LocalDate nextDueDate = calculateNextDueDate(LocalDate.parse(task.getDueDate()), task.getRepeat());
                Task nextTask = new Task(
                        task.getTitle(),
                        task.getDescription(),
                        nextDueDate.toString(),
                        task.getCategory(),
                        task.getPriority(),
                        false, // Not completed
                        task.getDepends(),
                        task.getRepeat()
                );
                taskList.add(nextTask);
                System.out.println("Next occurrence added: " + nextTask.getTitle() + " (Due: " + nextDueDate + ")");
            }
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static LocalDate calculateNextDueDate(LocalDate currentDate, String interval) {
        switch (interval) {
            case "daily":
                return currentDate.plusDays(1);
            case "weekly":
                return currentDate.plusWeeks(1);
            case "monthly":
                return currentDate.plusMonths(1);
            default:
                throw new IllegalArgumentException("Invalid recurrence interval: " + interval);
        }
    }
}
