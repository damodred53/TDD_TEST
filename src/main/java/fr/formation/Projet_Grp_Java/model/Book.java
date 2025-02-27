package fr.formation.Projet_Grp_Java.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    @Id
    @UuidGenerator
    @Column(name = "book_id")
    private String id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String author;

    @Column(nullable = true)
    private String publisher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookFormat format;

    @Column(nullable = false)
    private boolean isAvailable;
}
