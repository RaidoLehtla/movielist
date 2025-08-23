package ee.raido.movielist.persistence.usermovie;

import ee.raido.movielist.persistence.movie.Movie;
import ee.raido.movielist.persistence.useraccount.UserAccount;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "USER_MOVIE")
public class UserMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserAccount user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    @Size(max = 20)
    @NotNull
    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    @Column(name = "RATING")
    private Short rating;

    @Size(max = 1000)
    @Column(name = "COMMENT", length = 1000)
    private String comment;

    @Column(name = "WATCHED_AT")
    private LocalDate watchedAt;

    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

}