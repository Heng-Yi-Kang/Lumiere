
package com.mycompany.lumiere_maven;

import java.util.List;
import java.util.Scanner;

public class Lumiere
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        List<Task> tasks = loadData.getTasks();
        boolean status = true;
        while(status)
        {
            view.lines();
            loadData.initialise(input);
            email.checkDeadlines(tasks);
            System.out.printf("Welcome %s!\n", loadData.getUsername());
            System.out.println("Lumiere – your guide to a brighter, more organized life.");
            System.out.println("""
                             Action:
                             1. View tasks.
                             2. Create new task.
                             3. Complete a task.
                             4. Edit a task
                             5. Search for task(s).
                             6. Delete a task.
                             7. Analyse tasks.
                             8. Save and Exit.""");
            System.out.print("> ");
            int action = input.nextInt();
            input.nextLine();
            switch(action)
            {
                case 1:
                    view.viewTasks(tasks, input);
                    break;
                case 2:
                    addtask.createTask(tasks);
                    break;
                case 3:
                    CompleteTask2.markTaskAsComplete(input, tasks);
                    break;
                case 4:
                    EditTask.editTask(tasks, input);
                    break;
                case 5:
                    searchEngine.run(tasks, input);
                    break;
                case 6:
                    addtask.deleteTask(tasks, input);
                    break;
                case 7:
                    analyse.showCompletionStatus(tasks);
                    analyse.showCompletionRate(tasks);
                    analyse.showCategorizedTask(tasks);
                    break;
                case 8:
                    System.out.println("See you again :)");
                    status = false;
                    break;
            }
        }
        loadData.saveTasks(tasks);
    }
    
}
