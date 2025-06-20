package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.UserMovieLike;
import FIUBA.CineXplore.service.UserMovieLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Todo: Revisar si se podria usar DTOs para las respuestas
@RestController
@RequestMapping("/api/user-movie-likes")
public class UserMovieLikeController {

    private final UserMovieLikeService userMovieLikeService;

    public UserMovieLikeController(UserMovieLikeService userMovieLikeService) {
        this.userMovieLikeService = userMovieLikeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserMovieLike>> likeMovie(@RequestParam Long userId, @RequestParam Long movieId) {
        UserMovieLike like = userMovieLikeService.likeMovie(userId, movieId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Like registrado", like));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> unlikeMovie(@RequestParam Long userId, @RequestParam Long movieId) {
        userMovieLikeService.unlikeMovie(userId, movieId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Like eliminado", null));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<UserMovieLike>>> getLikesByUser(@PathVariable Long userId) {
        List<UserMovieLike> likes = userMovieLikeService.getLikesByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Likes del usuario", likes));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<UserMovieLike>>> getLikesByMovie(@PathVariable Long movieId) {
        List<UserMovieLike> likes = userMovieLikeService.getLikesByMovie(movieId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Likes de la película", likes));
    }
}