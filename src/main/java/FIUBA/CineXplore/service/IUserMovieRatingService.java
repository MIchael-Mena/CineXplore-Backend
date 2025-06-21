package FIUBA.CineXplore.service;

import FIUBA.CineXplore.model.UserMovieRating;

import java.util.List;

public interface IUserMovieRatingService {

    UserMovieRating rateMovie(Long userId, Long movieId, Byte rating);

    void deleteRating(Long userId, Long movieId);

    List<UserMovieRating> getRatingsByUser(Long userId);

    List<UserMovieRating> getRatingsByMovie(Long movieId);
}
