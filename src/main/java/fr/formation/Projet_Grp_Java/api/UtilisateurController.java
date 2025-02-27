package fr.formation.Projet_Grp_Java.api;

import fr.formation.Projet_Grp_Java.model.Utilisateur;
import fr.formation.Projet_Grp_Java.repo.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

        private final UtilisateurRepository utilisateurRepository;

        public UtilisateurController(UtilisateurRepository utilisateurRepository) {
                this.utilisateurRepository = utilisateurRepository;
        }

        @GetMapping
        public List<Utilisateur> getAllUtilisateurs() {
                return utilisateurRepository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable String id) {
                return utilisateurRepository.findById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
                return ResponseEntity.ok(utilisateurRepository.save(utilisateur));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable String id,
                        @RequestBody Utilisateur updatedUtilisateur) {
                Optional<Utilisateur> existingUtilisateur = utilisateurRepository.findById(id);

                if (existingUtilisateur.isPresent()) {
                        Utilisateur utilisateur = existingUtilisateur.get();
                        utilisateur.setNom(updatedUtilisateur.getNom());
                        utilisateur.setPrenom(updatedUtilisateur.getPrenom());
                        utilisateur.setDateDeNaissance(updatedUtilisateur.getDateDeNaissance());
                        utilisateur.setCivilite(updatedUtilisateur.getCivilite());

                        return ResponseEntity.ok(utilisateurRepository.save(utilisateur));
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUtilisateur(@PathVariable String id) {
                if (utilisateurRepository.existsById(id)) {
                        utilisateurRepository.deleteById(id);
                        return ResponseEntity.noContent().build();
                } else {
                        return ResponseEntity.notFound().build();
                }
        }
}
