package FIUBA.CineXplore.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class MovieActorId implements Serializable {
    private Long movieId;
    private Long actorId;

    public MovieActorId() {
    }

    public MovieActorId(Long movieId, Long actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    // equals, hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieActorId that)) return false;
        if (!movieId.equals(that.movieId)) return false;
        return actorId.equals(that.actorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, actorId);
    }
}