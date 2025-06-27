package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Director;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DirectorService implements IGenericService<Director> {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public Director save(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Director findById(Long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Director", id));
    }

    @Override
    public void deleteById(Long id) {
        if (!directorRepository.existsById(id)) {
            throw new EntityNotFoundException("Director", id);
        }
        directorRepository.deleteById(id);
    }

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Set<Movie> getDirectorMovies(Long directorId) {
        Director director = findById(directorId);
        return director.getMovies();
    }
}