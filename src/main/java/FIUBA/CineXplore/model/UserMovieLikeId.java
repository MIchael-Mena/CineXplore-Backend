package FIUBA.CineXplore.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class UserMovieLikeId implements Serializable {
    private Integer userId;
    private Integer movieId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMovieLikeId that)) return false;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }
}