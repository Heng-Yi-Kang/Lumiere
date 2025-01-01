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

public class email{
    private static final String EMAIL_HOST = "smtp.gmail.com";
    private static final int EMAIL_PORT = 587;
    private static final String EMAIL_USER = "jiakangkhe@gmail.com";
    private static final String EMAIL_PASSWORD = "efar orgq vzym lcjq";
    private static final String SENDER_EMAIL = "jiakangkhe@gmail.com";

    //Send email
    private void sendEmail(String toEmail, String subject, String body){
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", EMAIL_HOST);
        props.put("mail.smtp.port", EMAIL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(EMAIL_USER, EMAIL_PASSWORD);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Email sent to " + toEmail);
        }catch(MessagingException e){
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public void checkDeadlines(List<Task> tasks){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");

        for(Task task: tasks){
            LocalDateTime deadline = LocalDateTime.parse(task.getDueDate(), formatter);
            long hoursUntilDeadline = ChronoUnit.HOURS.between(now,deadline);

            if(hoursUntilDeadline > 0 && hoursUntilDeadline <= 24){
                String subject = "Reminder: Task '" + task.getTitle() + "' is due soon!";
                String body = "Task: " + task.getTitle() + "\n" + "Description: " + task.getDescription() + "\n" + "Deadline: " + task.getDueDate() + "\n" + "Please complete it before the deadline!";
                sendEmail(task.getEmail(), subject, body);
            }
        }

    }
}