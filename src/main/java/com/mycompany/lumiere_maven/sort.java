/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
/**
 *
 * @author arch_kang
 */
public class sort {
    public static void sortTasks(List<Task> tasks, Scanner input) {
        view.lines();
//        System.out.println("=== Sort Tasks ===");
        System.out.println("Sort by:");
        System.out.println("1. Due Date (Ascending)");
        System.out.println("2. Due Date (Descending)");
        System.out.println("3. Priority (High to Low)");
        System.out.println("4. Priority (Low to High)");
        System.out.print("> ");

        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1:
                tasks.sort(Comparator.comparing(Task::getDueDate));
                System.out.println("Tasks sorted by Due Date (Ascending)!");
                break;
            case 2:
                tasks.sort(Comparator.comparing(Task::getDueDate).reversed());
                System.out.println("Tasks sorted by Due Date (Descending)!");
                break;
            case 3:
            case 4:
                tasks.sort((task1, task2) -> {
                    int priority1 = getPriorityValue(task1.getPriority());
                    int priority2 = getPriorityValue(task2.getPriority());
                    return choice == 3 ? Integer.compare(priority2, priority1) // High to Low
                            : Integer.compare(priority1, priority2); // Low to High
                });
                System.out.println("Tasks sorted by Priority (" + (choice == 3 ? "High to Low" : "Low to High") + ")!");
                break;
            default:
                System.out.println("Invalid choice. No sorting applied.");
                return;
        }
    }

    public static int getPriorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "high":
                return 3;
            case "medium":
                return 2;
            case "low":
                return 1;
            default:
                return 0;
        }
    }
}
