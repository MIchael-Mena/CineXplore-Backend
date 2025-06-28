package FIUBA.CineXplore.security.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.security.dto.*;
import FIUBA.CineXplore.security.service.AuthService;
import FIUBA.CineXplore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        User user = userService.getUserByEmail(request.getEmail());
        ApiResponse<AuthResponseDTO> response = buildLoginResponse(user, token, 200, "Login exitoso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(@Valid @RequestBody SignUpRequest request) {
        User user = authService.createUser(request.getUsername(), request.getEmail(), request.getPassword());
        String token = authService.createToken(user);
        ApiResponse<AuthResponseDTO> response = buildLoginResponse(user, token, 201, "Usuario registrado exitosamente");
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenDTO>> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String newToken = authService.refreshToken(authHeader);
        TokenDTO tokenDTO = new TokenDTO(newToken);
        ApiResponse<TokenDTO> response = new ApiResponse<>(200, "Token renovado exitosamente", tokenDTO);
        return ResponseEntity.ok(response);
    }

    private ApiResponse<AuthResponseDTO> buildLoginResponse(User user, String token, int status, String message) {
        UserDTO userDTO = UserDTO.fromUser(user);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);
        authResponseDTO.setUser(userDTO);
        return new ApiResponse<>(status, message, authResponseDTO);
    }

}