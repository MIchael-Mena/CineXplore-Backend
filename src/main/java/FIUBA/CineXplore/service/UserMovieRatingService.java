package FIUBA.CineXplore.service;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import FIUBA.CineXplore.model.Movie;
import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.model.UserMovieRating;
import FIUBA.CineXplore.repository.MovieRepository;
import FIUBA.CineXplore.repository.UserMovieRatingRepository;
import FIUBA.CineXplore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserMovieRatingService implements IUserMovieRatingService {

    private final UserMovieRatingRepository userMovieRatingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public UserMovieRatingService(UserMovieRatingRepository userMovieRatingRepository,
                                  UserRepository userRepository,
                                  MovieRepository movieRepository) {
        this.userMovieRatingRepository = userMovieRatingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public UserMovieRating rateMovie(Long userId, Long movieId, Byte rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", userId));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie", movieId));

        // Si ya existe un rating para este usuario y película, actualizarlo
        UserMovieRating userMovieRating = userMovieRatingRepository
                .findByUser_UserIdAndMovie_MovieId(userId, movieId)
                .orElse(new UserMovieRating());
        userMovieRating.setUser(user);
        userMovieRating.setMovie(movie);
        userMovieRating.setRating(rating);
        userMovieRating.setRatedAt(LocalDateTime.now());
        return userMovieRatingRepository.save(userMovieRating);
    }

    @Override
    public void deleteRating(Long userId, Long movieId) {
        userMovieRatingRepository.findByUser_UserIdAndMovie_MovieId(userId, movieId)
                .ifPresent(userMovieRatingRepository::delete);
    }

    @Override
    public List<UserMovieRating> getRatingsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User", userId);
        }
        return userMovieRatingRepository.findAll()
                .stream()
                .filter(rating -> rating.getUser().getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<UserMovieRating> getRatingsByMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new EntityNotFoundException("Movie", movieId);
        }
        return userMovieRatingRepository.findAll()
                .stream()
                .filter(rating -> rating.getMovie().getMovieId().equals(movieId))
                .toList();
    }
}