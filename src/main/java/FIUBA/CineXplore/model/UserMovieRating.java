package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "UserMovieRating")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserMovieRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @NotNull
    @Min(1)
    @Max(10)
    @Column(nullable = false)
    private Byte rating;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime ratedAt = LocalDateTime.now();
}