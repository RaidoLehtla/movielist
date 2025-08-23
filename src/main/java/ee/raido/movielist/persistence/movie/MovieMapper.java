package ee.raido.movielist.persistence.movie;

import ee.raido.movielist.controller.movie.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto toDto(Movie entity);
}
