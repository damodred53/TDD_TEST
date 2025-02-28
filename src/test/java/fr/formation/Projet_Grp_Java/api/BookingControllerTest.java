package fr.formation.Projet_Grp_Java.api;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import fr.formation.Projet_Grp_Java.repo.BookingRepository;
import fr.formation.Projet_Grp_Java.service.BookingService;
import fr.formation.Projet_Grp_Java.service.EmailServiceMock;

public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EmailServiceMock emailServiceMock;

    @InjectMocks
    private BookController bookController;

}
