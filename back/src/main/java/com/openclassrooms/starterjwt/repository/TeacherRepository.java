package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data pour l'entité {@link Teacher}.
 * <p>
 * Hérite du CRUD standard (findById, findAll...).
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
