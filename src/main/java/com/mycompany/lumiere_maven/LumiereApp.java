package com.mycompany.lumiere_maven;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class LumiereApp {
    public static void main(String[] args) {
        // Setup main frame
        JFrame frame = new JFrame("Lumiere - Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the task list (you can load data from a file or database)
        List<Task> tasks = new ArrayList<>();

        // Create main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add buttons for user interaction
        JButton viewButton = new JButton("View Tasks");
        JButton addButton = new JButton("Create Task");
        JButton editButton = new JButton("Edit Task");
        JButton sortButton = new JButton("Sort Tasks");
        JButton exitButton = new JButton("Exit");

        // Button action listeners
        viewButton.addActionListener(e -> viewTasks(tasks));
        addButton.addActionListener(e -> addTask(tasks));
        editButton.addActionListener(e -> editTask(tasks));
        sortButton.addActionListener(e -> sortTasks(tasks));
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(exitButton);

        // Add button panel to main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void viewTasks(List<Task> tasks) {
        StringBuilder taskList = new StringBuilder();
        for (Task task : tasks) {
            taskList.append(task.getTitle())
                    .append(" - ")
                    .append(task.getStatus() ? "Completed" : "Incomplete")
                    .append("\n");
        }

        // Display tasks in a dialog box
        JOptionPane.showMessageDialog(null, taskList.toString(), "All Tasks", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void addTask(List<Task> tasks) {
        // Show dialog for adding a new task (could be replaced by a full form)
        String title = JOptionPane.showInputDialog("Enter task title:");
        if (title != null && !title.trim().isEmpty()) {
            Task newTask = new Task(tasks.size() + 1, title, "", new java.util.Date(), "Work", "Medium", false, "", "", false);
            tasks.add(newTask);
            JOptionPane.showMessageDialog(null, "Task added successfully!");
        }
    }

    private static void editTask(List<Task> tasks) {
        String taskList = "";
        for (int i = 0; i < tasks.size(); i++) {
            taskList += (i + 1) + ". " + tasks.get(i).getTitle() + "\n";
        }

        String taskNumStr = JOptionPane.showInputDialog("Select task to edit:\n" + taskList);
        if (taskNumStr != null) {
            int taskNum = Integer.parseInt(taskNumStr) - 1;
            if (taskNum >= 0 && taskNum < tasks.size()) {
                Task task = tasks.get(taskNum);
                String newTitle = JOptionPane.showInputDialog("Enter new title for task:", task.getTitle());
                if (newTitle != null) {
                    task.setTitle(newTitle);
                    JOptionPane.showMessageDialog(null, "Task updated successfully!");
                }
            }
        }
    }

    private static void sortTasks(List<Task> tasks) {
        // Sorting tasks by priority or date can be done similarly
        tasks.sort((t1, t2) -> t1.getPriority().compareTo(t2.getPriority()));
        JOptionPane.showMessageDialog(null, "Tasks sorted!");
    }
}