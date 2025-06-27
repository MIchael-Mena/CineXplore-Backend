package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.UserMovieComment;
import FIUBA.CineXplore.service.UserMovieCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class UserMovieCommentController {

    private final UserMovieCommentService userMovieCommentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userId")
    public ResponseEntity<ApiResponse<UserMovieComment>> addComment(
            @RequestParam Long userId,
            @RequestParam Long movieId,
            @Valid @RequestBody String commentText) {
        UserMovieComment comment = userMovieCommentService.addComment(userId, movieId, commentText);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comentario registrado", comment));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @userMovieCommentService.isCommentOwner(#commentId, authentication.principal.id)")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId) {
        userMovieCommentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comentario eliminado", null));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userId")
    public ResponseEntity<ApiResponse<List<UserMovieComment>>> getCommentsByUser(@PathVariable Long userId) {
        List<UserMovieComment> comments = userMovieCommentService.getCommentsByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comentarios del usuario", comments));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<UserMovieComment>>> getCommentsByMovie(@PathVariable Long movieId) {
        List<UserMovieComment> comments = userMovieCommentService.getCommentsByMovie(movieId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comentarios de la película", comments));
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @userMovieCommentService.isCommentOwner(#commentId, authentication.principal.id)")
    public ResponseEntity<ApiResponse<UserMovieComment>> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody String commentText) {
        UserMovieComment updatedComment = userMovieCommentService.updateComment(commentId, commentText);
        return ResponseEntity.ok(new ApiResponse<>(200, "Comentario actualizado", updatedComment));
    }
}