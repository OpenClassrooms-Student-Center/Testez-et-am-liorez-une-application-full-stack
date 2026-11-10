package com.openclassrooms.starterjwt.controllers;


import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST des sessions ({@code /api/session}).
 * <p>
 * Rôle : recevoir la requête HTTP, appeler le service, convertir Entity ↔ DTO.
 * Les erreurs ({@code NumberFormatException}, not found, etc.) sont gérées
 * par {@link com.openclassrooms.starterjwt.exception.GlobalExceptionHandler}.
 */
@RestController
@RequestMapping("/api/session")
@Log4j2
public class SessionController {
    private final SessionMapper sessionMapper;
    private final SessionService sessionService;

    /**
     * @param sessionService service métier des sessions
     * @param sessionMapper  conversion Session ↔ SessionDto
     */
    public SessionController(SessionService sessionService,
                             SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
        this.sessionService = sessionService;
    }

    /**
     * Récupère une session par id.
     *
     * @param id identifiant (String, converti en Long)
     * @return la session en DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> findById(@PathVariable("id") String id) {
        Session session = this.sessionService.getById(Long.valueOf(id));
        return ResponseEntity.ok().body(this.sessionMapper.toDto(session));
    }

    /**
     * Liste toutes les sessions.
     *
     * @return liste de {@link SessionDto}
     */
    @GetMapping()
    public ResponseEntity<List<SessionDto>> findAll() {
        List<Session> sessions = this.sessionService.findAll();
        return ResponseEntity.ok().body(this.sessionMapper.toDto(sessions));
    }

    /**
     * Crée une session.
     *
     * @param sessionDto données de création (name, date, teacher_id, description)
     * @return la session créée
     */
    @PostMapping()
    public ResponseEntity<SessionDto> create(@Valid @RequestBody SessionDto sessionDto) {
        // log.info(sessionDto);

        Session session = this.sessionService.create(this.sessionMapper.toEntity(sessionDto));

        //log.info(session);
        return ResponseEntity.ok().body(this.sessionMapper.toDto(session));
    }

    /**
     * Met à jour une session.
     *
     * @param id         id de la session
     * @param sessionDto nouvelles données
     * @return la session mise à jour
     */
    @PutMapping("{id}")
    public ResponseEntity<SessionDto> update(@PathVariable("id") String id, @Valid @RequestBody SessionDto sessionDto) {
        Session session = this.sessionService.update(Long.parseLong(id), this.sessionMapper.toEntity(sessionDto));
        return ResponseEntity.ok().body(this.sessionMapper.toDto(session));
    }

    /**
     * Supprime une session.
     *
     * @param id id de la session
     * @return 200 sans corps
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        this.sessionService.delete(Long.parseLong(id));
        return ResponseEntity.ok().build();
    }

    /**
     * Inscrit un utilisateur à une session.
     *
     * @param id     id de la session
     * @param userId id de l'utilisateur
     * @return 200 sans corps
     */
    @PostMapping("{id}/participate/{userId}")
    public ResponseEntity<Void> participate(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        this.sessionService.participate(Long.parseLong(id), Long.parseLong(userId));
        return ResponseEntity.ok().build();
    }

    /**
     * Désinscrit un utilisateur d'une session.
     *
     * @param id     id de la session
     * @param userId id de l'utilisateur
     * @return 200 sans corps
     */
    @DeleteMapping("{id}/participate/{userId}")
    public ResponseEntity<Void> noLongerParticipate(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        this.sessionService.noLongerParticipate(Long.parseLong(id), Long.parseLong(userId));
        return ResponseEntity.ok().build();
    }
}
