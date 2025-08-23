package ee.raido.movielist.service.movie;

import ee.raido.movielist.controller.movie.dto.MovieDto;
import ee.raido.movielist.persistence.movie.Movie;
import ee.raido.movielist.persistence.movie.MovieMapper;
import ee.raido.movielist.persistence.movie.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository repo;
    private final MovieMapper mapper;

    public MovieService(MovieRepository repo, MovieMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public MovieDto getById(Integer id) {
        Movie m = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie " + id + " not found"));
        return mapper.toDto(m);
    }
}
