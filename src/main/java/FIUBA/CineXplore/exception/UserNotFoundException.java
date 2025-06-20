package FIUBA.CineXplore.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Usuario no encontrado con id: " + userId);
    }
}