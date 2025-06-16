package FIUBA.CineXplore.repository;

import FIUBA.CineXplore.model.UserMovieComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieCommentRepository extends JpaRepository<UserMovieComment, Long> {
    
}
