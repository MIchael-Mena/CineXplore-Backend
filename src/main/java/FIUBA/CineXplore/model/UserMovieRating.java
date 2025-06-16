package FIUBA.CineXplore.model;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private Byte rating;

    @Column(nullable = false, updatable = false)
    private LocalDateTime ratedAt = LocalDateTime.now();

}