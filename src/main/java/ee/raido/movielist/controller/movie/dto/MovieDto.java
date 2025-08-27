package ee.raido.movielist.controller.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;

public record MovieDto(@JsonProperty(access = JsonProperty.Access.READ_ONLY) Integer id,

                       String title, short releaseYear, short runtimeMinutes,

                       @JsonProperty(access = JsonProperty.Access.READ_ONLY) Instant createdAt,

                       // user-specific
                       @JsonProperty(access = JsonProperty.Access.READ_ONLY) Integer userId,
                       @JsonProperty(access = JsonProperty.Access.READ_ONLY) String status,
                       @JsonProperty(access = JsonProperty.Access.READ_ONLY) Short rating,
                       @JsonProperty(access = JsonProperty.Access.READ_ONLY) String comment,
                       @JsonProperty(access = JsonProperty.Access.READ_ONLY) LocalDate watchedAt) {
}
