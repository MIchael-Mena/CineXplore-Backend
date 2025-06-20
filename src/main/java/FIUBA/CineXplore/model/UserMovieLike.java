package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


// Se usa esta tabla intermedia para representar la relación muchos a muchos entre usuarios y películas que les gustan
// solo es necesario ya que se esta agregando un campo adicional likedAt para registrar cuándo se le dio like a la película
// de lo contrario, se podría haber usado una relación muchos a muchos directa entre User y Movie sin necesidad de esta clase
@Table(name = "UserMovieLike")
@IdClass(UserMovieLikeId.class) // Se usa una clave primaria compuesta
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserMovieLike {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "movie_id")
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