package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier des professeurs (teachers).
 * <p>
 * Lecture seule dans cette application (pas de create/update/delete exposés).
 */
@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    /**
     * @param teacherRepository accès à la table {@code teachers}
     */
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Liste tous les professeurs.
     *
     * @return liste des teachers
     */
    public List<Teacher> findAll() {
        return this.teacherRepository.findAll();
    }

    /**
     * Recherche un professeur par id.
     *
     * @param id identifiant du teacher
     * @return un {@link Optional} contenant le teacher s'il existe
     */
    public Optional<Teacher> findById(Long id) {
        return this.teacherRepository.findById(id);
    }

    /**
     * Récupère un professeur obligatoire par id.
     *
     * @param id identifiant du teacher
     * @return le teacher trouvé
     * @throws NotFoundException si le teacher n'existe pas
     */
    public Teacher getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with id: " + id));
    }
}
