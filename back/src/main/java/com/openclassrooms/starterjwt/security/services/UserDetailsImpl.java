package com.openclassrooms.starterjwt.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Représentation Spring Security d'un utilisateur authentifié.
 * <p>
 * Contient les infos utiles après login (id, email, nom, admin...) sans exposer
 * toute l'entité JPA. Placée dans le {@code SecurityContext} après validation du JWT
 * ou de l'authentification username/password.
 */
@Builder
@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    /** Identifiant technique en base. */
    private Long id;

    /** Email (utilisé comme username Spring Security). */
    private String username;

    private String firstName;

    private String lastName;

    /** Indique si le compte est administrateur. */
    private Boolean admin;

    /** Mot de passe hashé ; ignoré en JSON. */
    @JsonIgnore
    private String password;

    /**
     * Rôles / autorités Spring Security.
     * <p>
     * Ici volontairement vide : l'app gère le flag admin à part.
     *
     * @return collection d'authorities (vide)
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>();
    }

    /** @return toujours {@code true} (pas d'expiration de compte gérée) */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** @return toujours {@code true} (pas de verrouillage géré) */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** @return toujours {@code true} (pas d'expiration credentials gérée) */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** @return toujours {@code true} (compte toujours actif) */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Deux UserDetails sont égaux s'ils ont le même id.
     *
     * @param o autre objet
     * @return {@code true} si même id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
