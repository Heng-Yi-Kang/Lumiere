package com.mycompany.lumiere_maven;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class analyse{
    
    public static void showCompletionStatus(List<Task> tasks){
        int completedTasks = 0;
        int pendingTasks = 0;

        for(Task task : tasks){
            if(task.getStatus()){
                completedTasks++;
            }else{
                pendingTasks++;
            }
        }

        System.out.println("--- Task Completion Status ---");
        System.out.println("Completed Tasks: " + completedTasks);
        System.out.println("Pending Tasks: " + pendingTasks);
    }

    public static void showCompletionRate(List<Task> tasks){
        int totalTasks = tasks.size();
        int completedTasks = 0;

        for(Task task : tasks){
            if(task.getStatus()){
                completedTasks++;
            }
        }

        double completionRate = (double)completedTasks / totalTasks * 100;
        System.out.println("\n --- Task Completion Rate ---");
        System.out.printf("Task Completion Rate: %.2f%%\n", completionRate);
    }

    public static void showCategorizedTask(List<Task> tasks){
        Map<String, Integer> categoryCount = new HashMap<>();

        for(Task task : tasks){
            String category = task.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        System.out.println("\n --- Categorized Task Count ---");
        for(Map.Entry<String, Integer> entry : categoryCount.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue() + " tasks");
        }

    }
}