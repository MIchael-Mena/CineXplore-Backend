package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Actor;
import FIUBA.CineXplore.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService implements IGenericService<Actor> {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Actor findById(Long id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actor", id));
    }

    @Override
    public void deleteById(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new EntityNotFoundException("Actor", id);
        }
        actorRepository.deleteById(id);
    }

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }
}
