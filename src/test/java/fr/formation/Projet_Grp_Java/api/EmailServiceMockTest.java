package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Booking;
import fr.formation.Projet_Grp_Java.model.Utilisateur;
import fr.formation.Projet_Grp_Java.model.Book;
import fr.formation.Projet_Grp_Java.repo.BookingRepository;
import fr.formation.Projet_Grp_Java.service.EmailServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceMockTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EmailServiceMock emailServiceMock;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking;

    @BeforeEach
    void setUp() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("Dupont");

        Book book = new Book();
        book.setTitle("Les Misérables");

        booking = new Booking();
        booking.setUtilisateur(utilisateur);
        booking.setBook(book);
        booking.setDueDate(LocalDate.now().minusDays(1));
    }

    @Test
    void testSendOverdueReminders_AucuneReservationEnRetard() {
        when(bookingRepository.findByDueDateBeforeAndIsEndedFalse(any())).thenReturn(Collections.emptyList());

        ResponseEntity<String> response = bookingController.sendOverdueReminders();

        assertEquals("Aucune réservation en retard.", response.getBody());

        verify(emailServiceMock, never()).sendMockEmail(any());
    }

    @Test
    void testSendOverdueReminders_ReservationsEnRetard() {
        when(bookingRepository.findByDueDateBeforeAndIsEndedFalse(any())).thenReturn(List.of(booking));

        ResponseEntity<String> response = bookingController.sendOverdueReminders();

        assertEquals("Rappels envoyés à 1 utilisateur(s).", response.getBody());

    }

    @Test
    void testSendMockEmail() {
        emailServiceMock.sendMockEmail(booking);

        verify(emailServiceMock, times(1)).sendMockEmail(booking);
        verify(emailServiceMock, times(1)).sendMockEmail(booking);
    }

}
