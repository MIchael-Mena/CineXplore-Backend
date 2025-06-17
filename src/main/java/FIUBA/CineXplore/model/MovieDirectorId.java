package FIUBA.CineXplore.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class MovieDirectorId implements Serializable {
    private Long movieId;
    private Long directorId;

    public MovieDirectorId() {
    }

    public MovieDirectorId(Long movieId, Long directorId) {
        this.movieId = movieId;
        this.directorId = directorId;
    }

    // equals, hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDirectorId that)) return false;
        if (!movieId.equals(that.movieId)) return false;
        return directorId.equals(that.directorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, directorId);
    }

}