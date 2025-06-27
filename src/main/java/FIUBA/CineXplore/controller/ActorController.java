package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.Actor;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Actor>>> getAllActors() {
        List<Actor> actors = actorService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de actores", actors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Actor>> getActorById(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Actor encontrado", actor));
    }

    @GetMapping("/{actorId}/movies")
    public ResponseEntity<ApiResponse<Set<Movie>>> getActorMovies(@PathVariable Long actorId) {
        Set<Movie> movies = actorService.getActorMovies(actorId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Películas del actor", movies)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Actor>> createActor(@RequestBody Actor actor) {
        Actor saved = actorService.save(actor);
        return ResponseEntity.status(201).body(
                new ApiResponse<>(201, "Actor creado", saved)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Actor>> updateActor(@PathVariable Long id, @RequestBody Actor actorDetails) {
        Actor actor = actorService.findById(id);
        actor.setFullName(actorDetails.getFullName());
        actor.setBirthDate(actorDetails.getBirthDate());
        Actor updated = actorService.save(actor);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Actor actualizado", updated)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteActor(@PathVariable Long id) {
        actorService.deleteById(id); // Lanza excepción si no existe
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Actor eliminado", null)
        );
    }
}