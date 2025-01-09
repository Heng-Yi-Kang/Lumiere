/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.*;
    
public class Dependency {
    
//    public List<Task> tasks;
    
    private static boolean isDependencyCycle(Task task, Task current){
        if (task == current){
            return true;
        }
        for (Task dependency : task.getDependsOn()){
            if (isDependencyCycle(dependency, current)){
                return true;
            }
        }
        return false;       
    }          
    
//    public Dependency(){
//        tasks = new ArrayList<>();
//    }
    
    public static void addDependency(List<Task> tasks, int dependentTaskNumber, int dependencyTaskNumber) 
    {
        if (dependentTaskNumber < 1 || dependentTaskNumber > tasks.size() ||
            dependencyTaskNumber < 1 || dependencyTaskNumber > tasks.size()) {
            System.out.println("Invalid task numbers.");
            return;
        }

        Task dependentTask = tasks.get(dependentTaskNumber - 1);
        Task dependencyTask = tasks.get(dependencyTaskNumber - 1);

        if (dependentTask == dependencyTask) {
            System.out.println("Error: A task cannot depend on itself.");
            return;
        }
        
        if (isDependencyCycle(dependencyTask, dependentTask)) {
            System.out.println("Error: Adding this dependency creates a cycle.");
            return;
        }

        dependentTask.addDependency(dependencyTask);
        System.out.println("Task \"" + dependentTask.getTitle() + "\" now depends on \"" + dependencyTask.getTitle() + "\".");
    }
    
      public static void generateDependency(List<Task> tasks)
      {
          for (Task task : tasks)
          {
              String s = task.getDependsId();
              if (s.compareTo("")!=0)
              {
                  String[] ids = task.getDependsId().split(",");
                  for (String id_str : ids) 
                  {
                      int id = Integer.parseInt(id_str);
                      for (Task dependedTask : tasks) 
                      {
                          int taskId = dependedTask.getId();
                          if (taskId == id) task.addDependency(dependedTask);
                      }
                  }
              }
          }
      }
      
      public static String saveDependency(Task task)
      {
          String ids = "";
          for (Task t : task.getDependsOn())
          {
              ids += t.getId() + ",";
          }
          return ids;
      }

    public static void manageDependencies(List<Task> tasks, Task task, Scanner sc) {
        while (true) {
            System.out.println();
            System.out.println("""
                    Manage Dependencies:
                    1. View Current Dependencies
                    2. Add Dependency
                    3. Remove Dependency
                    4. Replace Dependency
                    5. Back""");
            System.out.print(">");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: {
                    List<Task> dependencies = task.getDependsOn();
                    if (dependencies.isEmpty()) {
                        System.out.println("No dependencies.");
                    } else {
                        System.out.println("Current Dependencies:");
                        for (int i = 0; i < dependencies.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, dependencies.get(i).getTitle());
                        }
                    }
                }
                case 2: {
                    System.out.println("Available tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task t = tasks.get(i);
                        System.out.printf("%d. %s\n", i + 1, t.getTitle());
                    }
                    System.out.print("Enter task number to add as a dependency: ");
                    int dependencyTaskNumber = sc.nextInt();
                    Dependency.addDependency(tasks, task.getId(), dependencyTaskNumber);
                }
                case 3: {
                    List<Task> dependencies = task.getDependsOn();
                    if (dependencies.isEmpty()) {
                        System.out.println("No dependencies to remove.");
                    } else {
                        System.out.println("Current Dependencies:");
                        for (int i = 0; i < dependencies.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, dependencies.get(i).getTitle());
                        }
                        System.out.print("Enter dependency number to remove: ");
                        int depNumber = sc.nextInt();
                        if (depNumber > 0 && depNumber <= dependencies.size()) {
                            Task toRemove = dependencies.get(depNumber - 1);
                            dependencies.remove(toRemove);
                            System.out.println("Dependency removed successfully.");
                        } else {
                            System.out.println("Invalid dependency number.");
                        }
                    }
                }
                case 4: {
                    List<Task> dependencies = task.getDependsOn();
                    if (dependencies.isEmpty()) {
                        System.out.println("No dependencies to replace. Adding as a new dependency.");
                    } else {
                        System.out.println("Current Dependencies:");
                        for (int i = 0; i < dependencies.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, dependencies.get(i).getTitle());
                        }
                        System.out.print("Enter dependency number to replace: ");
                        int depNumber = sc.nextInt();
                        sc.nextLine();

                        if (depNumber < 1 || depNumber > dependencies.size()) {
                            System.out.println("Invalid dependency number.");
                            break;
                        }

                        System.out.println("Available tasks:");
                        for (int i = 0; i < tasks.size(); i++) {
                            Task t = tasks.get(i);
                            System.out.printf("%d. %s\n", i + 1, t.getTitle());
                        }
                        System.out.print("Enter new task number to replace with: ");
                        int newDependencyNumber = sc.nextInt();
                        sc.nextLine();

                        Task newDependency = tasks.get(newDependencyNumber - 1);

                        if (newDependency == task) {
                            System.out.println("Error: A task cannot depend on itself.");
                        } else if (Dependency.isDependencyCycle(newDependency, task)) {
                            System.out.println("Error: Replacing this dependency creates a cycle.");
                        } else {
                            dependencies.set(depNumber - 1, newDependency);
                            System.out.println("Dependency replaced successfully.");
                        }
                    }
                }
                case 5: {
                    return;
                }
                default: System.out.println("Invalid choice.");
            }
        }
    }
}


