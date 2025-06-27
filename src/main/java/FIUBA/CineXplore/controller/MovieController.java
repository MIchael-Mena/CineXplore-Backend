package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.dto.MovieRequestDTO;
import FIUBA.CineXplore.dto.MovieResponseDTO;
import FIUBA.CineXplore.model.Actor;
import FIUBA.CineXplore.model.Director;
import FIUBA.CineXplore.model.Genre;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.service.ActorService;
import FIUBA.CineXplore.service.DirectorService;
import FIUBA.CineXplore.service.GenreService;
import FIUBA.CineXplore.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final ActorService actorService;


    //    GET /api/movies                           # Todas las películas
//    GET /api/movies?title=batman              # Películas con "batman" en el título
//    GET /api/movies?genre=accion&minRating=7  # Películas de acción con rating >= 7
//    GET /api/movies?director=nolan&releaseYear=2010  # Películas de Nolan del 2010
//    GET /api/movies?minDuration=120&maxDuration=180  # Películas entre 2-3 horas
    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponseDTO>>> getAllMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String actor,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) BigDecimal minRating) {

        List<Movie> movies = movieService.searchMovies(title, genre, director, actor,
                minDuration, maxDuration, releaseYear, minRating);
        List<MovieResponseDTO> dtos = movies.stream()
                .map(MovieResponseDTO::fromMovie)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de películas", dtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponseDTO>> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.findById(id); // Lanza excepción si no existe
        return ResponseEntity.ok(new ApiResponse<>(200, "Película encontrada", MovieResponseDTO.fromMovie(movie)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{movieId}/actors/{actorId}")
    public ResponseEntity<ApiResponse<Void>> addActorToMovie(
            @PathVariable Long movieId,
            @PathVariable Long actorId) {
        movieService.addActorToMovie(movieId, actorId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Actor agregado a la película", null)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{movieId}/actors/{actorId}")
    public ResponseEntity<ApiResponse<Void>> removeActorFromMovie(
            @PathVariable Long movieId,
            @PathVariable Long actorId) {
        movieService.removeActorFromMovie(movieId, actorId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Actor removido de la película", null)
        );
    }

    @GetMapping("/{movieId}/actors")
    public ResponseEntity<ApiResponse<Set<Actor>>> getMovieActors(@PathVariable Long movieId) {
        Set<Actor> actors = movieService.getMovieActors(movieId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Actores de la película", actors)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{movieId}/directors/{directorId}")
    public ResponseEntity<ApiResponse<Void>> addDirectorToMovie(
            @PathVariable Long movieId,
            @PathVariable Long directorId) {
        movieService.addDirectorToMovie(movieId, directorId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Director agregado a la película", null)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{movieId}/directors/{directorId}")
    public ResponseEntity<ApiResponse<Void>> removeDirectorFromMovie(
            @PathVariable Long movieId,
            @PathVariable Long directorId) {
        movieService.removeDirectorFromMovie(movieId, directorId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Director removido de la película", null)
        );
    }

    @GetMapping("/{movieId}/directors")
    public ResponseEntity<ApiResponse<Set<Director>>> getMovieDirectors(@PathVariable Long movieId) {
        Set<Director> directors = movieService.getMovieDirectors(movieId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Directores de la película", directors)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{movieId}/genres/{genreId}")
    public ResponseEntity<ApiResponse<Void>> addGenreToMovie(
            @PathVariable Long movieId,
            @PathVariable Long genreId) {
        movieService.addGenreToMovie(movieId, genreId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Género agregado a la película", null)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{movieId}/genres/{genreId}")
    public ResponseEntity<ApiResponse<Void>> removeGenreFromMovie(
            @PathVariable Long movieId,
            @PathVariable Long genreId) {
        movieService.removeGenreFromMovie(movieId, genreId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Género removido de la película", null)
        );
    }

    @GetMapping("/{movieId}/genres")
    public ResponseEntity<ApiResponse<Set<Genre>>> getMovieGenres(@PathVariable Long movieId) {
        Set<Genre> genres = movieService.getMovieGenres(movieId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Géneros de la película", genres)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponseDTO>> createMovie(@Valid @RequestBody MovieRequestDTO dto) {
        Movie movie = mapDtoToMovie(new Movie(), dto);
        if (movie == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "IDs de directores, géneros o actores inválidos", null));
        }
        Movie saved = movieService.save(movie);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Película creada", MovieResponseDTO.fromMovie(saved)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponseDTO>> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequestDTO dto) {
        Movie movie = movieService.findById(id);
        movie = mapDtoToMovie(movie, dto);
        if (movie == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "IDs de directores, géneros o actores inválidos", null));
        }
        Movie updated = movieService.save(movie);
        return ResponseEntity.ok(new ApiResponse<>(200, "Película actualizada", MovieResponseDTO.fromMovie(updated)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Película eliminada", null));
    }

    // --- Métodos auxiliares ---

    private Movie mapDtoToMovie(Movie movie, MovieRequestDTO dto) {
        movie.setTitle(dto.title);
        movie.setCoverUrl(dto.coverUrl);
        movie.setDescription(dto.description);
        movie.setDurationMin(dto.durationMin);
        movie.setReleaseDate(dto.releaseDate);

        // Directores
        if (dto.directorIds != null && !dto.directorIds.isEmpty()) {
            Set<Director> directors = dto.directorIds.stream()
                    .map(directorService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            if (directors.size() != dto.directorIds.size()) return null;
            movie.setDirectors(directors);
        } else {
            movie.setDirectors(Collections.emptySet());
        }

        // Géneros
        if (dto.genreIds != null && !dto.genreIds.isEmpty()) {
            Set<Genre> genres = dto.genreIds.stream()
                    .map(genreService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            if (genres.size() != dto.genreIds.size()) return null;
            movie.setGenres(genres);
        } else {
            movie.setGenres(Collections.emptySet());
        }

        // Actores
        if (dto.actorIds != null && !dto.actorIds.isEmpty()) {
            Set<Actor> actors = dto.actorIds.stream()
                    .map(actorService::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            if (actors.size() != dto.actorIds.size()) return null;
            movie.setActors(actors);
        } else {
            movie.setActors(Collections.emptySet());
        }
        return movie;
    }

}