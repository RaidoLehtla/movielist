package ee.raido.movielist.service.movie;

import ee.raido.movielist.controller.movie.dto.MovieDto;
import ee.raido.movielist.persistence.movie.Movie;
import ee.raido.movielist.persistence.movie.MovieMapper;
import ee.raido.movielist.persistence.movie.MovieRepository;
import ee.raido.movielist.persistence.usermovie.UserMovie;
import ee.raido.movielist.persistence.usermovie.UserMovieRepository;
import ee.raido.movielist.persistence.useraccount.UserAccount;
import ee.raido.movielist.persistence.useraccount.UserAccountRepository;
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
    private final UserAccountRepository userAccountRepository;

    public MovieService(MovieRepository movieRepository,
                        MovieMapper movieMapper,
                        UserMovieRepository userMovieRepository,
                        UserAccountRepository userAccountRepository) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.userMovieRepository = userMovieRepository;
        this.userAccountRepository = userAccountRepository;
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
                .collect(Collectors.toMap(usermovie -> usermovie.getMovie().getId(), Function.identity(), (a, b) -> a));

        return movies.stream()
                .map(dto -> {
                    UserMovie usermovie = userMoviesByMovieId.get(dto.id());
                    return dtoWithUser(dto, userId, usermovie);
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

    public MovieDto upsertUserState(Integer movieId, MovieDto dto) {
        if (dto.userId() == null) {
            throw new IllegalArgumentException("userId is required");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie " + movieId + " not found"));
        UserAccount user = userAccountRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User " + dto.userId() + " not found"));

        UserMovie userMovie = userMovieRepository.findByUser_IdAndMovie_Id(user.getId(), movie.getId())
                .orElseGet(() -> {
                    UserMovie usermovie = new UserMovie();
                    usermovie.setUser(user);
                    usermovie.setMovie(movie);
                    usermovie.setCreatedAt(java.time.Instant.now());
                    return usermovie;
                });

        if (dto.status() != null) userMovie.setStatus(dto.status());
        if (dto.rating() != null) userMovie.setRating(dto.rating());
        if (dto.comment() != null) userMovie.setComment(dto.comment());
        if (dto.watchedAt() != null) userMovie.setWatchedAt(dto.watchedAt());

        userMovieRepository.save(userMovie);

        MovieDto baseDto = movieMapper.toDto(movie);
        return dtoWithUser(baseDto, user.getId(), userMovie);
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
