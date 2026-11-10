package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bridge Spring Security ↔ base de données.
 * <p>
 * Charge un {@link User} par email et le transforme en {@link UserDetailsImpl}
 * utilisé pendant le login et la validation JWT.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /** Accès direct au repository (couche sécurité Spring, hors controllers). */
    UserRepository userRepository;

    /**
     * @param userRepository repository des utilisateurs
     */
    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge l'utilisateur pour Spring Security à partir de son email.
     *
     * @param username email saisi au login (ou extrait du JWT)
     * @return {@link UserDetailsImpl} avec id, noms, admin, password
     * @throws UsernameNotFoundException si aucun user ne correspond
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

        return UserDetailsImpl
                .builder()
                .id(user.getId())
                .username(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .admin(user.isAdmin())
                .password(user.getPassword())
                .build();
    }

}
