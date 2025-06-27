package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Genre;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreService implements IGenericService<Genre> {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre findById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Género", id));
    }

    @Override
    public void deleteById(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new EntityNotFoundException("Género", id);
        }
        genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Set<Movie> getGenreMovies(Long genreId) {
        Genre genre = findById(genreId);
        return genre.getMovies();
    }
}
