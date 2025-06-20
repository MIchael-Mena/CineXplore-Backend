package FIUBA.CineXplore.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long commentId) {
        super("Comentario no encontrado con id: " + commentId);
    }
}
