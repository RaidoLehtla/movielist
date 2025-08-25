package ee.raido.movielist.persistence.usermovie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMovieRepository extends JpaRepository<UserMovie, Integer> {
    List<UserMovie> findByUser_Id(Integer userId);
    Optional<UserMovie> findByUser_IdAndMovie_Id(Integer userId, Integer movieId);
}