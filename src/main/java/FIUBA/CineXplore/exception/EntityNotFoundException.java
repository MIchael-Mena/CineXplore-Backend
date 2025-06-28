package FIUBA.CineXplore.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, Long id) {
        super(entity + " no encontrado con id: " + id);
    }

    public EntityNotFoundException(String entity, String id) {
        super(entity + " no encontrado con id: " + id);
    }
}