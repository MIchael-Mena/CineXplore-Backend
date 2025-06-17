package FIUBA.CineXplore.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class MovieGenreId implements Serializable {
    private Long movieId;
    private Long genreId;

    public MovieGenreId() {
    }

    public MovieGenreId(Long movieId, Long genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    // equals, hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieGenreId that)) return false;
        if (!movieId.equals(that.movieId)) return false;
        return genreId.equals(that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, genreId);
    }
}