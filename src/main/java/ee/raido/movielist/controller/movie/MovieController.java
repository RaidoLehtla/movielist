package ee.raido.movielist.controller.movie;

import ee.raido.movielist.controller.movie.dto.MovieDto;
import ee.raido.movielist.service.movie.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService service;
    public MovieController(MovieService service) { this.service = service; }

    @GetMapping("/{id}")
    public MovieDto findById(@PathVariable Integer id) {
        return service.getById(id);
    }
}
