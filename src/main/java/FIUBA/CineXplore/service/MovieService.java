package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.repository.MovieRepository;
import org.springframework.stereotype.Service;

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
}
