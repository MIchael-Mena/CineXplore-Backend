package FIUBA.CineXplore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@IdClass(MovieDirectorId.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDirector {
    @Id
    @Column(name = "movie_id")
    private Long movieId;

    @Id
    @Column(name = "director_id")
    private Long directorId;

    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "director_id", insertable = false, updatable = false)
    private Director director;

}