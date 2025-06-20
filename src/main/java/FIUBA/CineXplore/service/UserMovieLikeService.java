package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.MovieNotFoundException;
import FIUBA.CineXplore.exception.UserNotFoundException;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.model.UserMovieLike;
import FIUBA.CineXplore.model.UserMovieLikeId;
import FIUBA.CineXplore.repository.MovieRepository;
import FIUBA.CineXplore.repository.UserMovieLikeRepository;
import FIUBA.CineXplore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMovieLikeService implements IUserMovieLikeService {

    private final UserMovieLikeRepository userMovieLikeRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public UserMovieLikeService(UserMovieLikeRepository userMovieLikeRepository,
                                UserRepository userRepository,
                                MovieRepository movieRepository) {
        this.userMovieLikeRepository = userMovieLikeRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public UserMovieLike likeMovie(Long userId, Long movieId) {
        UserMovieLikeId id = new UserMovieLikeId(userId, movieId);
        if (userMovieLikeRepository.existsById(id)) {
            return userMovieLikeRepository.findById(id).get();
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
        UserMovieLike like = new UserMovieLike();
        like.setUserId(userId);
        like.setMovieId(movieId);
        like.setUser(user);
        like.setMovie(movie);
        return userMovieLikeRepository.save(like);
    }

    @Override
    public void unlikeMovie(Long userId, Long movieId) {
        UserMovieLikeId id = new UserMovieLikeId(userId, movieId);
        userMovieLikeRepository.deleteById(id);
    }

    @Override
    public List<UserMovieLike> getLikesByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return userMovieLikeRepository.findAll()
                .stream()
                .filter(like -> like.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<UserMovieLike> getLikesByMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new MovieNotFoundException(movieId);
        }
        return userMovieLikeRepository.findAll()
                .stream()
                .filter(like -> like.getMovieId().equals(movieId))
                .toList();
    }
}