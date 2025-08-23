package ee.raido.movielist.controller.movie.dto;

public record MovieDto(
        String title,
        short releaseYear,
        short runtimeMinutes
) {}

