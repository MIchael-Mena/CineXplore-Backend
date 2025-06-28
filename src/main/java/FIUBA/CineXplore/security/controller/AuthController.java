package FIUBA.CineXplore.security.controller;

import FIUBA.CineXplore.dto.ApiResponse;
import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.security.dto.LoginRequest;
import FIUBA.CineXplore.security.dto.SignUpRequest;
import FIUBA.CineXplore.security.dto.TokenDTO;
import FIUBA.CineXplore.security.service.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenDTO>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        TokenDTO tokenDTO = new TokenDTO(token);
        ApiResponse<TokenDTO> response = new ApiResponse<>(200, "Login exitoso", tokenDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<TokenDTO>> register(@Valid @RequestBody SignUpRequest request) {
        User user = authService.createUser(request.getUsername(), request.getEmail(), request.getPassword());
        String token = authService.createToken(user);
        TokenDTO tokenDTO = new TokenDTO(token);
        ApiResponse<TokenDTO> response = new ApiResponse<>(201, "Usuario registrado", tokenDTO);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenDTO>> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String newToken = authService.refreshToken(authHeader);
        TokenDTO tokenDTO = new TokenDTO(newToken);
        ApiResponse<TokenDTO> response = new ApiResponse<>(200, "Token renovado exitosamente", tokenDTO);
        return ResponseEntity.ok(response);
    }

}