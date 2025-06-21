package FIUBA.CineXplore.repository;

import FIUBA.CineXplore.model.UserMovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {

    // convención findBy[Relacion]_[CampoPK]And[Relacion]_[CampoPK].
    Optional<UserMovieRating> findByUser_UserIdAndMovie_MovieId(Long userId, Long movieId);
}