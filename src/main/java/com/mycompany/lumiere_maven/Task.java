
package com.mycompany.lumiere_maven;

public class Task 
{
    private String title, description, due_date, category, priority, depends_on, 
            repeat, email;
    private boolean status;
    
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
        this.due_date = preprocessDate(due_date);
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.depends_on = dependency;
        this.repeat = repeat;
    }

    private String preprocessDate(String date){
        if(date.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
            return date + " 00:00:00";
        }
        return date;
    }
    
    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return this.description; }
    public void setDescription(String des) { this.description = des; }
    
    public String getDueDate() { return this.due_date; }
    public void setDueDate(String date) { this.due_date = preprocessDate(date); }
    
    public String getCategory() { return this.category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getPriority() { return this.priority; }
    public void setPriority(String priority) { this.priority = priority; }
    
    public boolean getStatus() { return this.status; }
    public void setStatus(boolean status) { this.status = status; }
            
    public String getDepends() { return this.depends_on; }
    public void setDepends(String depends_on) { this.depends_on = depends_on; }
    
    public String getRepeat() { return this.repeat; }
    public void setRepeat(String repeat) { this.repeat = repeat; }
}
