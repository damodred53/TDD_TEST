package fr.formation.Projet_Grp_Java.service;

public class EmailServiceMock {

    public void sendEmail(String to, String subject, String message) {

        System.out.println("À : " + to);
        System.out.println("Sujet : " + subject);
        System.out.println("Message :\n" + message);
    }
}
