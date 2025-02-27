package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Utilisateur;
import fr.formation.Projet_Grp_Java.service.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UtilisateurControllerTest {

    @Mock
    private UtilisateurService utilisateurService;

    @InjectMocks
    private UtilisateurController utilisateurController;

    private Utilisateur utilisateur;
    private Utilisateur utilisateur2;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setId("1");
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Jean");
        utilisateur.setDateDeNaissance(LocalDate.of(1990, 5, 15));
        utilisateur.setCivilite("M.");

        utilisateur2 = new Utilisateur();
        utilisateur2.setId("2");
        utilisateur2.setNom("Dupon2");
        utilisateur2.setPrenom("Jea2");
        utilisateur2.setDateDeNaissance(LocalDate.of(1990, 5, 15));
        utilisateur2.setCivilite("M.");
    }

    @Test
    void testGetAllUtilisateurs() {

        List<Utilisateur> utilisateurs = Arrays.asList(utilisateur, utilisateur2);
        when(utilisateurService.getAllUtilisateurs()).thenReturn(utilisateurs);

        List<Utilisateur> result = utilisateurService.getAllUtilisateurs();

        assertEquals(2, result.size());
        assertEquals("Dupont", result.get(0).getNom());
    }
}
