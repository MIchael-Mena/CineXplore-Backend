package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.Actor;
import FIUBA.CineXplore.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService implements IGenericService<Actor> {

/*  Inyección de dependencias por campo, facil pero no recomendado
    @Autowired
    private ActorRepository actorRepository;
*/

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
        return actorRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

}
