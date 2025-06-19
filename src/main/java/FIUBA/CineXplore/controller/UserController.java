package FIUBA.CineXplore.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario creado exitosamente", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Usuario no encontrado", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario encontrado", user));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Lista de usuarios", users));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, "Usuario no encontrado", null));
        }
        // Solo se permite actualizar username y email
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        User updated = userService.save(user);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario actualizado exitosamente", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Usuario eliminado exitosamente", null));
    }
}