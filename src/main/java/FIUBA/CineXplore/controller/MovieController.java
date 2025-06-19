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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final ActorService actorService;

    public MovieController(MovieService movieService, DirectorService directorService,
                           GenreService genreService, ActorService actorService) {
        this.movieService = movieService;
        this.directorService = directorService;
        this.genreService = genreService;
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponseDTO>>> getAllMovies() {
        List<Movie> movies = movieService.findAll();
        List<MovieResponseDTO> dtos = movies.stream().map(this::toResponseDTO).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de películas", dtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponseDTO>> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Película no encontrada", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Película encontrada", this.toResponseDTO(movie)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponseDTO>> createMovie(@Valid @RequestBody MovieRequestDTO dto) {
        Movie movie = mapDtoToMovie(new Movie(), dto);
        if (movie == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "IDs de directores, géneros o actores inválidos", null));
        }
        Movie saved = movieService.save(movie);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Película creada", this.toResponseDTO(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponseDTO>> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequestDTO dto) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Película no encontrada", null));
        }
        movie = mapDtoToMovie(movie, dto);
        if (movie == null) {
            return ResponseEntity.status(400).body(new ApiResponse<>(400, "IDs de directores, géneros o actores inválidos", null));
        }
        Movie updated = movieService.save(movie);
        return ResponseEntity.ok(new ApiResponse<>(200, "Película actualizada", this.toResponseDTO(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Película no encontrada", null));
        }
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

    private MovieResponseDTO toResponseDTO(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.movieId = movie.getMovieId();
        dto.title = movie.getTitle();
        dto.description = movie.getDescription();
        dto.durationMin = movie.getDurationMin();
        dto.releaseDate = movie.getReleaseDate();
        dto.averageRating = movie.getAverageRating();
        dto.createdAt = movie.getCreatedAt();
        dto.directors = movie.getDirectors() != null
                ? movie.getDirectors().stream().map(Director::getFullName).collect(Collectors.toSet())
                : Collections.emptySet();
        dto.genres = movie.getGenres() != null
                ? movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())
                : Collections.emptySet();
        dto.actors = movie.getActors() != null
                ? movie.getActors().stream().map(Actor::getFullName).collect(Collectors.toSet())
                : Collections.emptySet();
        return dto;
    }
}