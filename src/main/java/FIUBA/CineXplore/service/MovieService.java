package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Actor;
import FIUBA.CineXplore.model.Director;
import FIUBA.CineXplore.model.Genre;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MovieService implements IGenericService<Movie> {

    private final MovieRepository movieRepository;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final GenreService genreService;

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Película", id));
    }

    @Override
    public void deleteById(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new EntityNotFoundException("Película", id);
        }
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public void updateAverageRating(Long movieId) {
        Movie movie = findById(movieId);
        var ratings = movie.getRatings(); // var se usa para inferir automáticamente el tipo de la variable
        if (ratings == null || ratings.isEmpty()) {
            movie.setAverageRating(BigDecimal.ZERO);
        } else {
            BigDecimal sum = ratings.stream()
                    .map(r -> BigDecimal.valueOf(r.getRating()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal avg = sum.divide(BigDecimal.valueOf(ratings.size()), 2, RoundingMode.HALF_UP);
            movie.setAverageRating(avg);
        }
        movieRepository.save(movie);
    }

    public List<Movie> searchMovies(String title, String genre, String director, String actor,
                                    Integer minDuration, Integer maxDuration,
                                    Integer releaseYear, BigDecimal minRating) {
        return movieRepository.findMoviesWithFilters(title, genre, director, actor,
                minDuration, maxDuration, releaseYear, minRating);
    }

    public void addActorToMovie(Long movieId, Long actorId) {
        addEntityToMovie(movieId, actorId, actorService, Movie::getActors);
    }

    public void removeActorFromMovie(Long movieId, Long actorId) {
        removeEntityFromMovie(movieId, actorId, actorService, Movie::getActors);
    }

    public Set<Actor> getMovieActors(Long movieId) {
        return getMovieEntities(movieId, Movie::getActors);
    }

    public void addDirectorToMovie(Long movieId, Long directorId) {
        addEntityToMovie(movieId, directorId, directorService, Movie::getDirectors);
    }

    public void removeDirectorFromMovie(Long movieId, Long directorId) {
        removeEntityFromMovie(movieId, directorId, directorService, Movie::getDirectors);
    }

    public Set<Director> getMovieDirectors(Long movieId) {
        return getMovieEntities(movieId, Movie::getDirectors);
    }

    public void addGenreToMovie(Long movieId, Long genreId) {
        addEntityToMovie(movieId, genreId, genreService, Movie::getGenres);
    }

    public void removeGenreFromMovie(Long movieId, Long genreId) {
        removeEntityFromMovie(movieId, genreId, genreService, Movie::getGenres);
    }

    public Set<Genre> getMovieGenres(Long movieId) {
        return getMovieEntities(movieId, Movie::getGenres);
    }

    // Métodos auxiliares genéricos
    private <T> void addEntityToMovie(Long movieId, Long entityId,
                                      IGenericService<T> service,
                                      Function<Movie, Set<T>> getter) {
        Movie movie = findById(movieId);
        T entity = service.findById(entityId);
        getter.apply(movie).add(entity);
        movieRepository.save(movie);
    }

    private <T> void removeEntityFromMovie(Long movieId, Long entityId,
                                           IGenericService<T> service,
                                           Function<Movie, Set<T>> getter) {
        Movie movie = findById(movieId);
        T entity = service.findById(entityId);
        getter.apply(movie).remove(entity);
        movieRepository.save(movie);
    }

    private <T> Set<T> getMovieEntities(Long movieId, Function<Movie, Set<T>> getter) {
        Movie movie = findById(movieId);
        return getter.apply(movie);
    }
}
