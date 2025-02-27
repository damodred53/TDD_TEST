package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Utilisateur;
import fr.formation.Projet_Grp_Java.repo.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilisateurControllerTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurController utilisateurController;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setId("1");
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Jean");
        utilisateur.setDateDeNaissance(LocalDate.of(1990, 5, 15));
        utilisateur.setCivilite("M.");
    }

    @Test
    void testGetAllUtilisateurs() {
        List<Utilisateur> utilisateurs = Arrays.asList(utilisateur);
        when(utilisateurRepository.findAll()).thenReturn(utilisateurs);

        List<Utilisateur> result = utilisateurController.getAllUtilisateurs();

        assertEquals(1, result.size());
        assertEquals("Dupont", result.get(0).getNom());
        verify(utilisateurRepository, times(1)).findAll();
    }

    @Test
    void testGetUtilisateurById_Found() {
        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(utilisateur));

        ResponseEntity<Utilisateur> response = utilisateurController.getUtilisateurById("1");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Dupont", response.getBody().getNom());
        verify(utilisateurRepository, times(1)).findById("1");
    }

    @Test
    void testGetUtilisateurById_NotFound() {
        when(utilisateurRepository.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<Utilisateur> response = utilisateurController.getUtilisateurById("1");

        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
        verify(utilisateurRepository, times(1)).findById("1");
    }

    @Test
    void testCreateUtilisateur() {
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        ResponseEntity<Utilisateur> response = utilisateurController.createUtilisateur(utilisateur);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Dupont", response.getBody().getNom());
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }

    @Test
    void testUpdateUtilisateur_Found() {
        Utilisateur updatedUser = new Utilisateur();
        updatedUser.setNom("Martin");
        updatedUser.setPrenom("Paul");
        updatedUser.setDateDeNaissance(LocalDate.of(1985, 3, 10));
        updatedUser.setCivilite("M.");

        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(updatedUser);

        ResponseEntity<Utilisateur> response = utilisateurController.updateUtilisateur("1", updatedUser);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("Martin", response.getBody().getNom());
        verify(utilisateurRepository, times(1)).findById("1");
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
    }

    @Test
    void testUpdateUtilisateur_NotFound() {
        Utilisateur updatedUser = new Utilisateur();
        updatedUser.setNom("Martin");

        when(utilisateurRepository.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<Utilisateur> response = utilisateurController.updateUtilisateur("1", updatedUser);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(utilisateurRepository, times(1)).findById("1");
        verify(utilisateurRepository, never()).save(any(Utilisateur.class));
    }

    @Test
    void testDeleteUtilisateur_Found() {
        when(utilisateurRepository.existsById("1")).thenReturn(true);
        doNothing().when(utilisateurRepository).deleteById("1");

        ResponseEntity<Void> response = utilisateurController.deleteUtilisateur("1");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(utilisateurRepository, times(1)).existsById("1");
        verify(utilisateurRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteUtilisateur_NotFound() {
        when(utilisateurRepository.existsById("1")).thenReturn(false);

        ResponseEntity<Void> response = utilisateurController.deleteUtilisateur("1");

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(utilisateurRepository, times(1)).existsById("1");
        verify(utilisateurRepository, never()).deleteById("1");
    }
}
