package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Booking;
import fr.formation.Projet_Grp_Java.repo.BookingRepository;
import fr.formation.Projet_Grp_Java.service.BookingService;
import fr.formation.Projet_Grp_Java.service.EmailServiceMock;
import fr.formation.Projet_Grp_Java.service.UtilisateurService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    private final BookingRepository bookingRepository;

    private final EmailServiceMock emailServiceMock;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository,
            UtilisateurService utilisateurService, EmailServiceMock emailServiceMock) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.emailServiceMock = emailServiceMock;
    }

    @PostMapping("/reminders")
    public ResponseEntity<String> sendOverdueReminders() {
        LocalDate today = LocalDate.now();

        List<Booking> expiredBookings = bookingRepository.findByDueDateBeforeAndIsEndedFalse(today);
        if (expiredBookings.isEmpty()) {
            return ResponseEntity.ok("Aucune réservation en retard.");
        }

        expiredBookings.forEach(booking -> emailServiceMock.sendMockEmail(booking));

        return ResponseEntity.ok("Rappels envoyés à " + expiredBookings.size() + " utilisateur(s).");
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
