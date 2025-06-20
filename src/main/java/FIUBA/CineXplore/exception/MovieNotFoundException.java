package FIUBA.CineXplore.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long movieId) {
        super("Película no encontrada con id: " + movieId);
    }
}