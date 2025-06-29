package FIUBA.CineXplore.model;

import FIUBA.CineXplore.security.model.Role;
import FIUBA.CineXplore.security.model.UserCredentials;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User implements UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty // Se puede permitir un usuario sin avatar
    @Size(max = 255)
    @Column(length = 255)
    private String avatarUrl;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String userName;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    // @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo letras y números")
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String passwordHash;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Past // Fecha de nacimiento debe ser en el pasado
    @Column
    private LocalDate birthDate; // "birthDate": "1990-01-01"

    @Size(max = 100)
    @Column(length = 100)
    private String country;

    @OneToMany(mappedBy = "user")
    private Set<UserMovieLike> likes;

    @OneToMany(mappedBy = "user")
    private Set<UserMovieRating> ratings;

    @OneToMany(mappedBy = "user")
    private Set<UserMovieComment> comments;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

/*    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private RoleName role = RoleName.USER;*/

}