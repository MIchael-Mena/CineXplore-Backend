package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.model.UserMovieComment;
import FIUBA.CineXplore.repository.MovieRepository;
import FIUBA.CineXplore.repository.UserMovieCommentRepository;
import FIUBA.CineXplore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserMovieCommentService implements IUserMovieCommentService {

    private final UserMovieCommentRepository userMovieCommentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public UserMovieCommentService(UserMovieCommentRepository userMovieCommentRepository,
                                   UserRepository userRepository,
                                   MovieRepository movieRepository) {
        this.userMovieCommentRepository = userMovieCommentRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public UserMovieComment addComment(Long userId, Long movieId, String commentText) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie", movieId));
        UserMovieComment comment = new UserMovieComment();
        comment.setUser(user);
        comment.setMovie(movie);
        comment.setCommentText(commentText);
        comment.setCreatedAt(LocalDateTime.now());
        // sentimentScore y aiTopics pueden ser calculados aquí si aplica
        return userMovieCommentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        userMovieCommentRepository.deleteById(commentId);
    }

    @Override
    public List<UserMovieComment> getCommentsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User", userId);
        }
        return userMovieCommentRepository.findAll()
                .stream()
                .filter(comment -> comment.getUser().getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<UserMovieComment> getCommentsByMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new EntityNotFoundException("Movie", movieId);
        }
        return userMovieCommentRepository.findAll()
                .stream()
                .filter(comment -> comment.getMovie().getMovieId().equals(movieId))
                .toList();
    }

    @Override
    public UserMovieComment updateComment(Long commentId, String commentText) {
        UserMovieComment comment = userMovieCommentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment", commentId));
        comment.setCommentText(commentText);
        comment.setCreatedAt(LocalDateTime.now());
        return userMovieCommentRepository.save(comment);
    }
}