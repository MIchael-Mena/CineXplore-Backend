package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Movie")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @NotBlank
    @Size(max = 200)
    @Column(length = 200, nullable = false)
    private String title;

    @Size(max = 500)
    @Column(length = 500)
    private String coverUrl;

    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Positive
    private Integer durationMin;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "movie")
    private Set<UserMovieLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private Set<UserMovieRating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserMovieComment> comments = new HashSet<>();

    // No usar CascadeType.REMOVE en las relaciones muchos a muchos, ya que esto eliminaría los registros de la tabla intermedia
    // La relación Muchos a Muchos creara una tabla intermedia automáticamente.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "MovieDirector",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>(); //

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "MovieGenre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "MovieActor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();
}