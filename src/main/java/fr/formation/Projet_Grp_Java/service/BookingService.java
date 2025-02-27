package fr.formation.Projet_Grp_Java.service;

import fr.formation.Projet_Grp_Java.model.Booking;
import fr.formation.Projet_Grp_Java.model.Utilisateur;
import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.repo.BookingRepository;
import fr.formation.Projet_Grp_Java.repo.UtilisateurRepository;
import fr.formation.Projet_Grp_Java.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private BookRepository bookRepository;

    public Booking createBooking(String userId, String bookId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        // Vérifier que l'utilisateur n'a pas plus de 3 réservations en cours
        long activeBookings = bookingRepository.findByUtilisateurId(userId)
                .stream()
                .filter(b -> !b.isEnded())
                .count();

        if (activeBookings >= 3) {
            throw new RuntimeException("L'utilisateur a déjà 3 réservations actives");
        }

        Booking booking = new Booking();
        booking.setUtilisateur(utilisateur);
        booking.setBook(book);
        booking.setReservationDate(LocalDate.now());
        booking.setDueDate(LocalDate.now().plusMonths(4));
        booking.setEnded(false);

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUtilisateurId(userId);
    }

    public void endBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        booking.setEnded(true);
        bookingRepository.save(booking);
    }

    public void deleteBooking(String bookingId) {
        bookingRepository.deleteById(bookingId);
    }

}
