package com.openclassrooms.starterjwt.mapper;

import java.util.List;

/**
 * Contrat générique de mapping entre un DTO ({@code D}) et une entité ({@code E}).
 * <p>
 * Les implémentations (MapStruct) convertissent :
 * <ul>
 *   <li>DTO → Entity : quand on reçoit une requête API</li>
 *   <li>Entity → DTO : quand on renvoie une réponse API</li>
 * </ul>
 *
 * @param <D> type du DTO
 * @param <E> type de l'entité JPA
 */
public interface EntityMapper<D, E> {

    /**
     * Convertit un DTO en entité.
     *
     * @param dto objet provenant de l'API
     * @return entité prête pour la couche métier / BDD
     */
    E toEntity(D dto);

    /**
     * Convertit une entité en DTO.
     *
     * @param entity objet issu de la BDD
     * @return DTO à renvoyer au client
     */
    D toDto(E entity);

    /**
     * Convertit une liste de DTO en liste d'entités.
     *
     * @param dtoList liste de DTO
     * @return liste d'entités
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Convertit une liste d'entités en liste de DTO.
     *
     * @param entityList liste d'entités
     * @return liste de DTO
     */
    List<D> toDto(List<E> entityList);
}
