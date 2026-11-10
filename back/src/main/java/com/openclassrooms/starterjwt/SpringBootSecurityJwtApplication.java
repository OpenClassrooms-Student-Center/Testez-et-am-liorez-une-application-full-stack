package com.openclassrooms.starterjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Point d'entrée de l'application Spring Boot Yoga App.
 * <p>
 * {@link EnableJpaAuditing} active le remplissage automatique des champs
 * {@code createdAt} / {@code updatedAt} sur les entités annotées.
 */
@SpringBootApplication
@EnableJpaAuditing
public class SpringBootSecurityJwtApplication {

    /**
     * Démarre le contexte Spring (API + Docker Compose MySQL si configuré).
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
    }
}
