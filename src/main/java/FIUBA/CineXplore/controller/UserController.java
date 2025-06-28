package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.service.RecommendationService;
import FIUBA.CineXplore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RecommendationService recommendationService;

/*    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario creado exitosamente", created));
    }*/

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario encontrado", user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de usuarios", users));
    }

/*    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userId")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        User user = userService.findById(id);
        user.setUserName(userDetails.getUserName());
        user.setEmail(userDetails.getEmail());
        User updated = userService.save(user);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario actualizado exitosamente", updated));
    }*/

    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario eliminado exitosamente", null));
    }


    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @GetMapping("/{id}/recommendations")
    public ResponseEntity<ApiResponse<List<Movie>>> getRecommendations(@PathVariable Long id) {
        List<Movie> recommendations = recommendationService.recommendMoviesForUser(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Películas recomendadas", recommendations));
    }
}