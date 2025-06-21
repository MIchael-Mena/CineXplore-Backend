package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MovieService implements IGenericService<Movie> {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

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
}
