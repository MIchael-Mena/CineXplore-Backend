package FIUBA.CineXplore.repository;

import FIUBA.CineXplore.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT m FROM Movie m " +
            "LEFT JOIN m.genres g " +
            "LEFT JOIN m.directors d " +
            "LEFT JOIN m.actors a " +
            "WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:genre IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :genre, '%'))) " +
            "AND (:director IS NULL OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :director, '%'))) " +
            "AND (:actor IS NULL OR LOWER(a.fullName) LIKE LOWER(CONCAT('%', :actor, '%'))) " +
            "AND (:minDuration IS NULL OR m.durationMin >= :minDuration) " +
            "AND (:maxDuration IS NULL OR m.durationMin <= :maxDuration) " +
            "AND (:releaseYear IS NULL OR YEAR(m.releaseDate) = :releaseYear) " +
            "AND (:minRating IS NULL OR m.averageRating >= :minRating)")
    List<Movie> findMoviesWithFilters(@Param("title") String title,
                                      @Param("genre") String genre,
                                      @Param("director") String director,
                                      @Param("actor") String actor,
                                      @Param("minDuration") Integer minDuration,
                                      @Param("maxDuration") Integer maxDuration,
                                      @Param("releaseYear") Integer releaseYear,
                                      @Param("minRating") BigDecimal minRating);

}
