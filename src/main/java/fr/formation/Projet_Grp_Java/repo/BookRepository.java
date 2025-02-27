package fr.formation.Projet_Grp_Java.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.Projet_Grp_Java.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);
}
