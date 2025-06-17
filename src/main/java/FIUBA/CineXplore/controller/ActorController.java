package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.Actor;
import FIUBA.CineXplore.service.ActorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Actor>>> getAllActors() {
        List<Actor> actors = actorService.findAll();
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Lista de actores", actors)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Actor>> getActorById(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        if (actor != null) {
            return ResponseEntity.ok(
                    new ApiResponse<>(200, "Actor encontrado", actor)
            );
        } else {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(404, "Actor no encontrado", null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Actor>> createActor(@RequestBody Actor actor) {
        Actor saved = actorService.save(actor);
        return ResponseEntity.status(201).body(
                new ApiResponse<>(201, "Actor creado", saved)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Actor>> updateActor(@PathVariable Long id, @RequestBody Actor actorDetails) {
        Actor actor = actorService.findById(id);
        if (actor == null) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(404, "Actor no encontrado", null)
            );
        }
        actor.setFullName(actorDetails.getFullName());
        actor.setBirthDate(actorDetails.getBirthDate());
        Actor updated = actorService.save(actor);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Actor actualizado", updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteActor(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        if (actor == null) {
            return ResponseEntity.status(404).body(
                    new ApiResponse<>(404, "Actor no encontrado", null)
            );
        }
        actorService.deleteById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Actor eliminado", null)
        );
    }
}
