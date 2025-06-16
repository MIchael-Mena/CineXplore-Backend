package FIUBA.CineXplore.repository;

import FIUBA.CineXplore.model.UserMovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {

}
