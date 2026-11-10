package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.exception.UnauthorizedException;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service métier des utilisateurs.
 * <p>
 * Contient la logique liée aux users : recherche, inscription, suppression de compte.
 * Les controllers appellent ce service ; ce service appelle le {@link UserRepository}.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * @param userRepository accès à la table {@code users}
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Recherche un utilisateur par son identifiant.
     *
     * @param id identifiant technique de l'utilisateur
     * @return un {@link Optional} contenant l'utilisateur s'il existe, sinon vide
     */
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    /**
     * Récupère un utilisateur par id, ou lève une exception s'il n'existe pas.
     *
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     * @throws NotFoundException si aucun utilisateur ne correspond à cet id
     */
    public User getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    /**
     * Recherche un utilisateur par email.
     *
     * @param email adresse email (également utilisée comme username JWT)
     * @return un {@link Optional} contenant l'utilisateur s'il existe
     */
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    /**
     * Indique si un email est déjà utilisé en base.
     *
     * @param email email à vérifier
     * @return {@code true} si l'email existe déjà
     */
    public boolean existsByEmail(String email) {
        return Boolean.TRUE.equals(this.userRepository.existsByEmail(email));
    }

    /**
     * Persiste un nouvel utilisateur (ou met à jour s'il a déjà un id).
     *
     * @param user entité à sauvegarder
     * @return l'utilisateur persisté (avec id généré si création)
     */
    public User create(User user) {
        return this.userRepository.save(user);
    }

    /**
     * Inscrit un nouvel utilisateur non-admin.
     *
     * @param email           email unique du compte
     * @param lastName        nom
     * @param firstName       prénom
     * @param encodedPassword mot de passe déjà hashé (BCrypt)
     * @return l'utilisateur créé
     * @throws BadRequestException si l'email est déjà pris
     */
    public User register(String email, String lastName, String firstName, String encodedPassword) {
        if (this.existsByEmail(email)) {
            throw new BadRequestException("Error: Email is already taken!");
        }

        User user = new User(email, lastName, firstName, encodedPassword, false);
        return this.create(user);
    }

    /**
     * Supprime un utilisateur par id (sans contrôle d'autorisation).
     *
     * @param id identifiant de l'utilisateur à supprimer
     */
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    /**
     * Supprime le compte de l'utilisateur connecté uniquement.
     * <p>
     * Vérifie que l'email du JWT correspond à l'email du compte ciblé.
     *
     * @param id               id du compte à supprimer
     * @param currentUserEmail email issu du token JWT (utilisateur connecté)
     * @throws NotFoundException     si le compte n'existe pas
     * @throws UnauthorizedException si on tente de supprimer le compte d'un autre
     */
    public void deleteAccount(Long id, String currentUserEmail) {
        User user = this.getById(id);

        if (!Objects.equals(currentUserEmail, user.getEmail())) {
            throw new UnauthorizedException("You are not allowed to delete this account");
        }

        this.delete(id);
    }
}
