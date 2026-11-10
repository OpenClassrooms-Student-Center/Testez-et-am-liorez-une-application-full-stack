package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Mapper MapStruct entre {@link TeacherDto} et {@link Teacher}.
 * <p>
 * Le code d'implémentation est généré à la compilation.
 */
@Component
@Mapper(componentModel = "spring")
public interface TeacherMapper extends EntityMapper<TeacherDto, Teacher> {
}
