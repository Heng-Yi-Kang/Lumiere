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
}


