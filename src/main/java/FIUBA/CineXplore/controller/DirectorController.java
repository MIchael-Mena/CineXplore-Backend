package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.Director;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Director>>> getAllDirectors() {
        List<Director> directors = directorService.findAll();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de directores", directors)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Director>> getDirectorById(@PathVariable Long id) {
        Director director = directorService.findById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Director encontrado", director)
        );
    }

    @GetMapping("/{directorId}/movies")
    public ResponseEntity<ApiResponse<Set<Movie>>> getDirectorMovies(@PathVariable Long directorId) {
        Set<Movie> movies = directorService.getDirectorMovies(directorId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Películas del director", movies)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Director>> createDirector(@RequestBody Director director) {
        Director saved = directorService.save(director);
        return ResponseEntity.status(201).body(
                new ApiResponse<>(201, "Director creado", saved)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Director>> updateDirector(@PathVariable Long id, @RequestBody Director directorDetails) {
        Director director = directorService.findById(id);
        director.setFullName(directorDetails.getFullName());
        director.setBirthDate(directorDetails.getBirthDate());
        Director updated = directorService.save(director);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Director actualizado", updated)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDirector(@PathVariable Long id) {
        directorService.deleteById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Director eliminado", null)
        );
    }
}