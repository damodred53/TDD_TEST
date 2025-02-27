package fr.formation.Projet_Grp_Java.service;

import org.springframework.stereotype.Service;

import fr.formation.Projet_Grp_Java.model.Booking;

@Service
public class EmailServiceMock {

    public void sendMockEmail(Booking booking) {
        System.out.println("Envoi d'un mail à : " + booking.getUtilisateur().getNom() +
                " pour livre : " + booking.getBook().getTitle() +
                " (Date limite max : " + booking.getDueDate() + ")");
    }
}
