package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.Director;
import FIUBA.CineXplore.repository.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return directorRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        directorRepository.deleteById(id);
    }

    @Override
    public List<Director> findAll() {
        return directorRepository.findAll();
    }
}
