package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Booking;
import fr.formation.Projet_Grp_Java.repo.BookingRepository;
import fr.formation.Projet_Grp_Java.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/reminders")
    public ResponseEntity<String> sendOverdueReminders() {
        LocalDate today = LocalDate.now();

        List<Booking> expiredBookings = bookingRepository.findByDueDateBeforeAndIsEndedFalse(today);
        if (expiredBookings.isEmpty()) {
            return ResponseEntity.ok("Aucune réservation en retard.");
        }

        expiredBookings.forEach(booking -> sendMockEmail(booking));

        return ResponseEntity.ok("Rappels envoyés à " + expiredBookings.size() + " utilisateur(s).");
    }

    private void sendMockEmail(Booking booking) {
        System.out.println("Envoi d'un mail à : " + booking.getUtilisateur().getNom() +
                " pour livre : " + booking.getBook().getTitle() +
                " (Date limite max : " + booking.getDueDate() + ")");
    }

    @PostMapping("/create/{userId}/{bookId}")
    public ResponseEntity<Booking> createBooking(@PathVariable String userId, @PathVariable String bookId) {
        return ResponseEntity.ok(bookingService.createBooking(userId, bookId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/end/{bookingId}")
    public ResponseEntity<String> endBooking(@PathVariable String bookingId) {
        bookingService.endBooking(bookingId);
        return ResponseEntity.ok("Réservation terminée avec succès !");
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable String bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok("Réservation supprimée avec succès !");
    }
}
