package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Utilisateur;
import fr.formation.Projet_Grp_Java.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

        private final UtilisateurService utilisateurService;

        public UtilisateurController(UtilisateurService utilisateurService) {
                this.utilisateurService = utilisateurService;
        }

        @GetMapping
        public List<Utilisateur> getAllUtilisateurs() {
                return utilisateurService.getAllUtilisateurs();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable String id) {
                return utilisateurService.getUtilisateurById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
                return ResponseEntity.ok(utilisateurService.createUtilisateur(utilisateur));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable String id,
                        @RequestBody Utilisateur updatedUtilisateur) {
                return utilisateurService.updateUtilisateur(id, updatedUtilisateur)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUtilisateur(@PathVariable String id) {
                return utilisateurService.deleteUtilisateur(id)
                                ? ResponseEntity.noContent().build()
                                : ResponseEntity.notFound().build();
        }
}
