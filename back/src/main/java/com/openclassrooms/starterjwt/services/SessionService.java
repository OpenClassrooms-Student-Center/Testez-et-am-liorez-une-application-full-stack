package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service métier des sessions de yoga.
 * <p>
 * Gère le CRUD des sessions et les inscriptions (participate / unparticipate).
 */
@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserService userService;

    /**
     * @param sessionRepository accès à la table {@code sessions}
     * @param userService       service users (pour résoudre les participants)
     */
    public SessionService(SessionRepository sessionRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    /**
     * Crée une nouvelle session en base.
     *
     * @param session entité session à persister
     * @return la session créée (avec id)
     */
    public Session create(Session session) {
        return this.sessionRepository.save(session);
    }

    /**
     * Liste toutes les sessions.
     *
     * @return liste des sessions
     */
    public List<Session> findAll() {
        return this.sessionRepository.findAll();
    }

    /**
     * Recherche une session par id.
     *
     * @param id identifiant de la session
     * @return un {@link Optional} contenant la session si elle existe
     */
    public Optional<Session> findById(Long id) {
        return this.sessionRepository.findById(id);
    }

    /**
     * Récupère une session obligatoire par id.
     *
     * @param id identifiant de la session
     * @return la session trouvée
     * @throws NotFoundException si la session n'existe pas
     */
    public Session getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new NotFoundException("Session not found with id: " + id));
    }

    /**
     * Met à jour une session existante.
     *
     * @param id      id de la session à modifier
     * @param session nouvelles données (l'id sera forcé à {@code id})
     * @return la session mise à jour
     * @throws NotFoundException si la session n'existe pas
     */
    public Session update(Long id, Session session) {
        this.getById(id);
        session.setId(id);
        return this.sessionRepository.save(session);
    }

    /**
     * Supprime une session après vérification de son existence.
     *
     * @param id id de la session à supprimer
     * @throws NotFoundException si la session n'existe pas
     */
    public void delete(Long id) {
        this.getById(id);
        this.sessionRepository.deleteById(id);
    }

    /**
     * Inscrit un utilisateur à une session.
     *
     * @param id     id de la session
     * @param userId id de l'utilisateur participant
     * @throws NotFoundException   si la session ou l'utilisateur n'existe pas
     * @throws BadRequestException si l'utilisateur est déjà inscrit
     */
    public void participate(Long id, Long userId) {
        Session session = this.getById(id);
        User user = this.userService.getById(userId);

        boolean alreadyParticipate = session.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if (alreadyParticipate) {
            throw new BadRequestException("User already participates in this session");
        }

        session.getUsers().add(user);
        this.sessionRepository.save(session);
    }

    /**
     * Désinscrit un utilisateur d'une session.
     *
     * @param id     id de la session
     * @param userId id de l'utilisateur à retirer
     * @throws NotFoundException   si la session n'existe pas
     * @throws BadRequestException si l'utilisateur n'était pas inscrit
     */
    public void noLongerParticipate(Long id, Long userId) {
        Session session = this.getById(id);

        boolean alreadyParticipate = session.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if (!alreadyParticipate) {
            throw new BadRequestException("User does not participate in this session");
        }

        session.setUsers(session.getUsers().stream()
                .filter(user -> !user.getId().equals(userId))
                .collect(Collectors.toList()));

        this.sessionRepository.save(session);
    }
}
