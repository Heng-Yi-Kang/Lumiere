/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lumiere_maven;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class searchEngine {
    private static void embedding(String query, List<Task> tasks) throws Exception 
    {
        String apiUrl = "https://api-inference.huggingface.co/models/sentence-transformers/all-MiniLM-L6-v2";
        Properties prop = new Properties();
        String apiToken;
        try (FileInputStream input = new FileInputStream("config.properties")) 
        {
            prop.load(input);
            apiToken = prop.getProperty("api_token");
        }
        
        ArrayList<String> descriptionList = new ArrayList<String>();
        for (Task t : tasks)
        {
            String input = String.format("\"%s\"", t.getDescription());
            descriptionList.add(input);
        }
        String allDescription = descriptionList.toString();

        String jsonInput = "{\"inputs\": { \"source_sentence\": \"" + query + "\", "
                + "\"sentences\": " + allDescription + " }}";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiToken);
        connection.setDoOutput(true);
        
        try (OutputStream os = connection.getOutputStream()) 
        {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) 
        {
            String response = new String(connection.getInputStream().readAllBytes());
            output(response, tasks);
        } 
        else 
        {
            System.out.println("Error: " + responseCode);
            String errorResponse = new String(connection.getErrorStream().readAllBytes());
            System.out.println("Error Response: " + errorResponse);
        }
    }
    
    private static void output(String response, List<Task> tasks)
    {
        String[] list = response.replaceAll("[\\[\\]]", "").split(",");
        double[] scores = new double[list.length];
        double n = 1000.0, sum = 0, avg;
        for(int i = 0; i < list.length; i++)
        {
            scores[i] = Math.round(Double.parseDouble(list[i]) * n) / n;
            sum += scores[i];
        }
        avg = sum / list.length;
        int cnt = 1;
        for(int j = 0; j < scores.length; j++)
        {
            if (scores[j] >= avg)
            {
                Task task = tasks.get(j);
                String status = (task.getStatus())?"Completed":"Incomplete";
                String title = task.getTitle();
                String date = task.getDateStr();
                String category = task.getCategory();
                System.out.printf("%d: [%-10s] %s (%5.3f)\nDue: %-15s \t Category: %s\n\n", 
                        cnt, status, title, scores[j], date, category);
                cnt++;
            }
        }
    }
    
    public static void run(List<Task> tasks, Scanner input)
    {
        view.lines();
        String query;
        try {
            System.out.print("Search by keyword: ");
            query = input.nextLine();
            System.out.println("Loading...");
            searchEngine.embedding(query, tasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
