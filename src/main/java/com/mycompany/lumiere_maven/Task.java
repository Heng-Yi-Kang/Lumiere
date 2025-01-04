package com.mycompany.lumiere_maven;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Task {
    private String title, description, category, priority, repeat;
    private boolean status;
    private Date due_date;
    private String recurrenceInterval; // e.g., "daily", "weekly", "monthly"
    private int remainingOccurrences;
    private List<Task> depends_on; //

    // due_date(YYYY-MM-DD)
    // category: homework, personal, work
    // priority: low, medium, high
    // status: true for completed, false for incomplete
    // depends_on: list of tasks that this task depends on
    // repeat: daily, weekly, monthly, "" for none

    public Task(String title, String description, Date due_date,
                String category, String priority, boolean status,
                String repeat, String recurrenceInterval, int remainingOccurrences) {
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.depends_on = new ArrayList<>();
        this.repeat = repeat;
        this.recurrenceInterval = recurrenceInterval;
        this.remainingOccurrences = remainingOccurrences;
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

    public String getRepeat() { return this.repeat; }
    public void setRepeat(String repeat) { this.repeat = repeat; }

    public String getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public int getRemainingOccurrences() {
        return remainingOccurrences;
    }

    public void decrementOccurrences() {
        this.remainingOccurrences--;
    }

    public void addDependency(Task task) {
        if (task == this) {
            System.out.println("Error: A task cannot depend on itself.");
            return;
        }
        if (depends_on.contains(task)) {
            System.out.println("Task is already a dependency.");
            return;
        }
        depends_on.add(task);
        System.out.println("Task \"" + title + "\" now depends on \"" + task.getTitle() + "\".");
    }

}
