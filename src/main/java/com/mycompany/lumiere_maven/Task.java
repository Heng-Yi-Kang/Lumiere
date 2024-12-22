
package com.mycompany.lumiere_maven;

public class Task 
{
    public String title, description, due_date, category, priority, depends_on, 
            repeat;
    public boolean status;
    
    // due_date(YYYY-MM-DD)
    // category: homework, personal, work
    // priority: low, medium, high
    // status: true for completed, false for incomplete
    // depends_on: receive title of task that it depends on, "" for none
    // repeat: daily, weekly, monthly, "" for none
    
    public Task(String title, String description, String due_date, 
            String category, String priority, boolean status, String dependency, 
            String repeat)
    {
        this.title = title;
        this.description = description;
        this.due_date = due_date;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.depends_on = dependency;
        this.repeat = repeat;
    }
    
}
