package com.mycompany.lumiere_maven;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;
import java.io.IOException;

public class email{
    private static final String EMAIL_HOST = "smtp.gmail.com";
    private static final int EMAIL_PORT = 587;
    private static final String EMAIL_USER = "jiakangkhe@gmail.com";
    private static final String EMAIL_PASSWORD = "efar orgq vzym lcjq";
    

    private static String preprocessDate(String date){
        if(date.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
            return date + " 00:00:00";
        }
        return date;
    }
    
    private static String loadEmail(){
        Properties prop = new Properties();
        String email = "";
        try (FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            email = prop.getProperty("email");
        }
        catch(Exception e){System.out.println("Error with loading email: " + e.getMessage());}
        return email;
    }
        
    //Send email
    private static void sendEmail(String subject, String body){
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", EMAIL_HOST);
        props.put("mail.smtp.port", EMAIL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        String toEmail = loadEmail();
        Session session = Session.getInstance(props, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(EMAIL_USER, EMAIL_PASSWORD);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Email sent to " + toEmail);
        }catch(MessagingException e){
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public static void checkDeadlines(List<Task> tasks){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
        
        for(Task task: tasks){
            String date = preprocessDate(task.getDateStr());
            LocalDateTime deadline = LocalDateTime.parse(date, formatter);
            long hoursUntilDeadline = ChronoUnit.HOURS.between(now,deadline);

            if(hoursUntilDeadline > 0 && hoursUntilDeadline <= 24){
                String subject = "Reminder: Task '" + task.getTitle() + "' is due soon!";
//                String body = "Task: " + task.getTitle() + "\n" + "Description: " + task.getDescription() + "\n" + "Deadline: " + task.getDueDate() + "\n" + "Please complete it before the deadline!";
                String body = "";
                try{
                    FileInputStream input = new FileInputStream("resource/message.txt");
                    Scanner writer = new Scanner(input);
                    body = writer.nextLine().replace("[User's Name]", "Alex");
                    while(writer.hasNext()){
                        body += String.format("%s\n", writer.nextLine());
                        body = body.replace("[Task Name]", task.getTitle());
                        body = body.replace("[Due Date]", task.getDueDate().toString());
                    }
                    writer.close();
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
                sendEmail(subject, body);
            }
        }

    }
}