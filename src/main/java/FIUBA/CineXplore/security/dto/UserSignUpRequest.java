package FIUBA.CineXplore.security.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserSignUpRequest {
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo letras y números")
    @NotBlank
    private String password;

    @Size(max = 255)
    private String avatarUrl;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthDate;

    @Size(max = 100)
    private String country;
}