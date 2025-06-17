package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.Director;
import FIUBA.CineXplore.service.DirectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

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
        if (director != null) {
            return ResponseEntity.ok(
                    new ApiResponse<>(200, "Director encontrado", director)
            );
        } else {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(404, "Director no encontrado", null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Director>> createDirector(@RequestBody Director director) {
        Director saved = directorService.save(director);
        return ResponseEntity.status(201).body(
                new ApiResponse<>(201, "Director creado", saved)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Director>> updateDirector(@PathVariable Long id, @RequestBody Director directorDetails) {
        Director director = directorService.findById(id);
        if (director == null) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(404, "Director no encontrado", null)
            );
        }
        director.setFullName(directorDetails.getFullName());
        director.setBirthDate(directorDetails.getBirthDate());
        Director updated = directorService.save(director);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Director actualizado", updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDirector(@PathVariable Long id) {
        Director director = directorService.findById(id);
        if (director == null) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(404, "Director no encontrado", null)
            );
        }
        directorService.deleteById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Director eliminado", null)
        );
    }
}