package ee.raido.movielist.persistence.movie;

import ee.raido.movielist.controller.movie.dto.MovieDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto toDto(Movie entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Movie toEntity(MovieDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(MovieDto dto, @MappingTarget Movie entity);
}
