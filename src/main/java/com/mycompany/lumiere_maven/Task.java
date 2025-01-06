package com.mycompany.lumiere_maven;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.text.SimpleDateFormat;

public class Task {
    private int id;
    private String title, description, category, priority;
    private boolean status, emailStatus;
    private Date due_date;
    private String recurrenceInterval; // e.g., "daily", "weekly", "monthly"
//    private int remainingOccurrences;
    private List<Task> depends_on; 
    private String dependsId;
    

    // due_date(YYYY-MM-DD)
    // category: homework, personal, work
    // priority: low, medium, high
    // status: true for completed, false for incomplete
    // depends_on: list of tasks that this task depends on
    // repeat: daily, weekly, monthly, "" for none

    public Task(int id, String title, String description, Date due_date,
                String category, String priority, boolean status,
                String dependsId, String recurrenceInterval, boolean emailStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.dependsId = dependsId;
        this.recurrenceInterval = recurrenceInterval;
        this.depends_on = new ArrayList<>();
        this.emailStatus = emailStatus;
    }
    

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDueDate() { return this.due_date; }
    public void setDueDate(Date due_date) { this.due_date = due_date; }

    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return this.priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public boolean getStatus() { return this.status; }
    public void setStatus(boolean status) { this.status = status; }

    public List<Task> getDependsOn() { return this.depends_on; }
    public void setDependsOn(List<Task> depends_on) { this.depends_on = depends_on; }

    public String getRecurrenceInterval() { return recurrenceInterval; }
    public void setRecurrenceInterval(String interval) { this.recurrenceInterval = interval; }

//    public int getRemainingOccurrences() {
//        return remainingOccurrences;
//    }
//
//    public void decrementOccurrences() {
//        this.remainingOccurrences--;
//    }

    public void addDependency(Task task) {
        if (task == this) {
            System.out.println("Error: A task cannot depend on itself.");
            return;
        }
        if (depends_on.contains(task)) {
            System.out.println("Task is already a dependency.");
            return;
        }
        this.depends_on.add(task);
//        System.out.println("Task \"" + title + "\" now depends on \"" + task.getTitle() + "\".");
    }
    
    public String getDateStr(){
        return new SimpleDateFormat("yyyy-MM-dd").format(this.due_date);
    }
    
    public String getDependsStr()
    {
        String dependedTask = "";
        for (int i = 0; i < this.depends_on.size(); i++)
        {
           Task t = this.depends_on.get(i);
           dependedTask += t.getTitle() + ((i==this.depends_on.size()-1)?"":",");
        }
        return dependedTask;
    }
    
    public int getId() { return this.id; }
    
    public String getDependsId() { return this.dependsId; }
    
    public boolean getEmailStatus() { return this.emailStatus; }
    public void setEmailStatus(boolean status) { this.emailStatus = status; }
}
