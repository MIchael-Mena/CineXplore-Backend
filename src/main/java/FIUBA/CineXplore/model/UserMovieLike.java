package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "UserMovieLike")
@IdClass(UserMovieLikeId.class) // Se usa una clave primaria compuesta
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserMovieLike {
    @Id
    private Long userId;

    @Id
    private Long movieId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    @Column(nullable = false, updatable = false)
    private LocalDateTime likedAt = LocalDateTime.now();

}