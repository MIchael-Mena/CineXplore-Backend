package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo letras y números")
    @NotBlank
    @Size(min = 8, max = 255)
    @Column(length = 255, nullable = false)
    private String passwordHash;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private Set<UserMovieLike> likes;

    @OneToMany(mappedBy = "user")
    private Set<UserMovieRating> ratings;

    @OneToMany(mappedBy = "user")
    private Set<UserMovieComment> comments;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserRole role = UserRole.USER;

}