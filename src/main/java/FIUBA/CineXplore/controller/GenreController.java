package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.Genre;
import FIUBA.CineXplore.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Genre>>> getAllGenres() {
        List<Genre> genres = genreService.findAll();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de géneros", genres)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Genre>> getGenreById(@PathVariable Long id) {
        Genre genre = genreService.findById(id); // Lanza excepción si no existe
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Género encontrado", genre)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Genre>> createGenre(@RequestBody Genre genre) {
        Genre saved = genreService.save(genre);
        return ResponseEntity.status(201).body(
                new ApiResponse<>(201, "Género creado", saved)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Genre>> updateGenre(@PathVariable Long id, @RequestBody Genre genreDetails) {
        Genre genre = genreService.findById(id); // Lanza excepción si no existe
        genre.setName(genreDetails.getName());
        Genre updated = genreService.save(genre);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Género actualizado", updated)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGenre(@PathVariable Long id) {
        genreService.deleteById(id); // Lanza excepción si no existe
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Género eliminado", null)
        );
    }
}