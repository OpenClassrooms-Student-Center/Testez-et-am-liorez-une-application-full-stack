package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data pour l'entité {@link Session}.
 * <p>
 * Hérite du CRUD standard (findById, save, deleteById, findAll...).
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
