package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST des utilisateurs ({@code /api/user}).
 * <p>
 * Permet de consulter un profil et de supprimer son propre compte.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * @param userService service métier utilisateurs
     * @param userMapper  conversion User ↔ UserDto
     */
    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    /**
     * Récupère un utilisateur par id.
     *
     * @param id identifiant utilisateur
     * @return {@link UserDto} (sans mot de passe exposé)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") String id) {
        User user = this.userService.getById(Long.valueOf(id));
        return ResponseEntity.ok().body(this.userMapper.toDto(user));
    }

    /**
     * Supprime le compte de l'utilisateur connecté uniquement.
     *
     * @param id id du compte à supprimer
     * @return 200 si OK, sinon 401/404 via le handler global
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.userService.deleteAccount(Long.parseLong(id), userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
