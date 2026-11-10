package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Spring Data pour l'entité {@link User}.
 * <p>
 * Fournit le CRUD de base ({@link JpaRepository}) + requêtes par email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Cherche un utilisateur par email.
     *
     * @param email email unique
     * @return Optional du user
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà.
     *
     * @param email email à tester
     * @return true si déjà utilisé
     */
    Boolean existsByEmail(String email);
}
