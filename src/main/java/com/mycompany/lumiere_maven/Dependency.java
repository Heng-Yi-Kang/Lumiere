/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;

import java.util.*;
    
public class Dependency {
    
    public List<Task> tasks;
    
    public boolean isDependencyCycle(Task task, Task current){
        if (task == current){
            return true;
        }
        for (Task dependency : task.getDependencies()){
            if (isDependencyCycle(dependency, current)){
                return true;
            }
        }
        return false;       
    }          
    
    public Dependency(){
        tasks = new ArrayList<>();
    }      
    
    public void addDependency(int dependentTaskNumber, int dependencyTaskNumber) {
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

        dependentTask.addDependency(dependencyTask);
        System.out.println("Task \"" + dependentTask.getTitle() + "\" now depends on \"" + dependencyTask.getTitle() + "\".");
    }
}


