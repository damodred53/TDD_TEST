package fr.formation.Projet_Grp_Java.service;

public class mockServiceWeb {

    private String mockIfNull(String value) {
        return (value == null || value.trim().isEmpty()) ? "test" : value;
    }
}
