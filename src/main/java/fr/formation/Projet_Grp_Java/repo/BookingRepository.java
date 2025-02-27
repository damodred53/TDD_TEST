package fr.formation.Projet_Grp_Java.repo;

import fr.formation.Projet_Grp_Java.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByDueDateBeforeAndIsEndedFalse(LocalDate today);

    List<Booking> findByUtilisateurId(String userId);
}
