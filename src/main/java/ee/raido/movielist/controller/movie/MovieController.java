package ee.raido.movielist.controller.movie;

import ee.raido.movielist.controller.movie.dto.MovieDto;
import ee.raido.movielist.infrastructure.rest.error.ApiError;
import ee.raido.movielist.service.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Movies", description = "Movie API")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @Operation(summary = "List movies")
    @ApiResponse(responseCode = "200", description = "OK")
    public List<MovieDto> findAll(@RequestParam(required = false) Integer userId) {
        return movieService.getAll(userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public MovieDto findById(@PathVariable Integer id, @RequestParam(required = false) Integer userId) {
        return movieService.getById(id, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created"), @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public MovieDto create(@RequestBody @Valid MovieDto dto) {
        return movieService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ApiError.class))), @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public MovieDto update(@PathVariable Integer id, @RequestBody @Valid MovieDto dto) {
        return movieService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public void delete(@PathVariable Integer id) {
        movieService.delete(id);
    }
}
