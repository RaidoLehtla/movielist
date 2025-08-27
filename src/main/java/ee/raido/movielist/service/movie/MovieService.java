package ee.raido.movielist.service.movie;

import ee.raido.movielist.controller.movie.dto.MovieDto;
import ee.raido.movielist.persistence.movie.Movie;
import ee.raido.movielist.persistence.movie.MovieMapper;
import ee.raido.movielist.persistence.movie.MovieRepository;
import ee.raido.movielist.persistence.usermovie.UserMovie;
import ee.raido.movielist.persistence.usermovie.UserMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final UserMovieRepository userMovieRepository;

    public MovieService(MovieRepository movieRepository,
                        MovieMapper movieMapper,
                        UserMovieRepository userMovieRepository) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.userMovieRepository = userMovieRepository;
    }

    public MovieDto getById(Integer movieId, Integer userId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie " + movieId + " not found"));
        MovieDto baseDto = movieMapper.toDto(movie);
        return attachUserState(baseDto, userId);
    }

    public List<MovieDto> getAll(Integer userId) {
        List<MovieDto> movies = movieRepository.findAll()
                .stream()
                .map(movieMapper::toDto)
                .toList();

        if (userId == null) {
            return movies;
        }

        Map<Integer, UserMovie> userMoviesByMovieId = userMovieRepository.findByUser_Id(userId)
                .stream()
                .collect(Collectors.toMap(
                        link -> link.getMovie().getId(),
                        Function.identity(),
                        (a, b) -> a
                ));

        return movies.stream()
                .map(dto -> {
                    UserMovie link = userMoviesByMovieId.get(dto.id());
                    return dtoWithUser(dto, userId, link);
                })
                .toList();
    }

    public MovieDto create(MovieDto dto) {
        Movie movie = movieMapper.toEntity(dto);
        Movie saved = movieRepository.save(movie);
        return movieMapper.toDto(saved);
    }

    public MovieDto update(Integer id, MovieDto dto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie " + id + " not found"));
        movieMapper.updateEntityFromDto(dto, movie);
        Movie updated = movieRepository.save(movie);
        return movieMapper.toDto(updated);
    }

    public void delete(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new IllegalArgumentException("Movie " + id + " not found");
        }
        movieRepository.deleteById(id);
    }

    private MovieDto attachUserState(MovieDto baseDto, Integer userId) {
        if (userId == null) return baseDto;
        var linkOpt = userMovieRepository.findByUser_IdAndMovie_Id(userId, baseDto.id());
        return dtoWithUser(baseDto, userId, linkOpt.orElse(null));
    }

    private MovieDto dtoWithUser(MovieDto baseDto, Integer userId, UserMovie userMovie) {
        return new MovieDto(
                baseDto.id(),
                baseDto.title(),
                baseDto.releaseYear(),
                baseDto.runtimeMinutes(),
                baseDto.createdAt(),
                userId,
                userMovie != null ? userMovie.getStatus() : null,
                userMovie != null ? userMovie.getRating() : null,
                userMovie != null ? userMovie.getComment() : null,
                userMovie != null ? userMovie.getWatchedAt() : null
        );
    }
}
