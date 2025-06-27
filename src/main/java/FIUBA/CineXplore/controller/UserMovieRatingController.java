package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.UserMovieRating;
import FIUBA.CineXplore.service.MovieService;
import FIUBA.CineXplore.service.UserMovieRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-movie-ratings")
@RequiredArgsConstructor
public class UserMovieRatingController {

    private final UserMovieRatingService userMovieRatingService;
    private final MovieService movieService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userId")
    public ResponseEntity<ApiResponse<UserMovieRating>> rateMovie(
            @RequestParam Long userId,
            @RequestParam Long movieId,
            @RequestParam Byte rating) {
        UserMovieRating userMovieRating = userMovieRatingService.rateMovie(userId, movieId, rating);
        movieService.updateAverageRating(movieId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Rating registrado", userMovieRating));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userId")
    public ResponseEntity<ApiResponse<Void>> deleteRating(
            @RequestParam Long userId,
            @RequestParam Long movieId) {
        userMovieRatingService.deleteRating(userId, movieId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Rating eliminado", null));
    }

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userId")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<UserMovieRating>>> getRatingsByUser(@PathVariable Long userId) {
        List<UserMovieRating> ratings = userMovieRatingService.getRatingsByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Ratings del usuario", ratings));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<UserMovieRating>>> getRatingsByMovie(@PathVariable Long movieId) {
        List<UserMovieRating> ratings = userMovieRatingService.getRatingsByMovie(movieId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Ratings de la película", ratings));
    }
}