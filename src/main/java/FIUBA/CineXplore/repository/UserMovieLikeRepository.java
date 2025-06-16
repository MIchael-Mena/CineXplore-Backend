package FIUBA.CineXplore.repository;

import FIUBA.CineXplore.model.UserMovieLike;
import FIUBA.CineXplore.model.UserMovieLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieLikeRepository extends JpaRepository<UserMovieLike, UserMovieLikeId> {

}
