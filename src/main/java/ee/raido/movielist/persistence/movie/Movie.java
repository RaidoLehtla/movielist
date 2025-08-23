package ee.raido.movielist.persistence.movie;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "MOVIE")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    @NotNull
    @Column(name = "RELEASE_YEAR", nullable = false)
    private Short releaseYear;

    @NotNull
    @Column(name = "RUNTIME_MINUTES", nullable = false)
    private Short runtimeMinutes;

    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

}